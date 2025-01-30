import pandas as pd
import glob
import warnings
import re

# Ignorar el aviso de la biblioteca openpyxl
warnings.filterwarnings("ignore")

# Obtener lista de archivos .xlsx en el directorio
archivos_xlsx = glob.glob('ficherosExcelOrigen/*.xlsx')
carpeta_de_salida = 'sqlSalida'

# Diccionarios para almacenar los datos
proyectos = {}
sprints = {}
tareas = {}
prompts = {}
estimaciones = {}

# Función para crear el SQL para insertar datos en cada tabla
def crear_sql_insert(tabla, datos):
    sql = f"INSERT INTO {tabla} ("
    columnas = ", ".join(datos.keys())
    valores = []
    for valor in datos.values():
        if isinstance(valor, str):
            valores.append(f"'{valor}'")
        else:
            valores.append(str(valor))
    valores = ", ".join(valores)
    sql += f"{columnas}) VALUES ({valores});"
    return sql



# Código principal

for archivo in archivos_xlsx:
    match = re.search(r'ficherosExcelOrigen\\DED-(.*?)_Adopcion IA_(.*?)', archivo)
    if match:
        nombre_fichero = match.group(1)
        print(f"-----------------------Procesando archivo {nombre_fichero}...")
        with open(f'{carpeta_de_salida}/{nombre_fichero}_proyectos.sql', 'w', encoding='utf-8') as archivo_proyecto, \
                open(f'{carpeta_de_salida}/{nombre_fichero}_sprints.sql', 'w', encoding='utf-8') as archivo_sprint, \
                open(f'{carpeta_de_salida}/{nombre_fichero}_tareas.sql', 'w', encoding='utf-8') as archivo_tarea, \
                open(f'{carpeta_de_salida}/{nombre_fichero}_prompts.sql', 'w', encoding='utf-8') as archivo_prompt, \
                open(f'{carpeta_de_salida}/{nombre_fichero}_estimaciones.sql', 'w', encoding='utf-8') as archivo_estimacion, \
                open(f'{carpeta_de_salida}/{nombre_fichero}_mediciones.sql', 'w', encoding='utf-8') as archivo_medicion:

            try:
                # Crear proyecto si no existe
                if not pd.isnull(nombre_fichero) and nombre_fichero != "" and nombre_fichero != "nan":
                    if nombre_fichero not in proyectos:
                        proyectos[nombre_fichero] = len(proyectos) + 1
                        archivo_proyecto.write(f"INSERT INTO proyecto (nombre) VALUES ('{nombre_fichero}');\n")

                # Crear prompts V2
                df2 = pd.read_excel(archivo, sheet_name='AdopcionIA')
                row = df2.iloc[2]
                columna = 6 if nombre_fichero == 'TESTING' else 7
                while not pd.isnull(row.iloc[columna]):
                    prompt = row.iloc[columna]
                    prompt_key = (proyectos[nombre_fichero], prompt)
                    if prompt_key not in prompts:
                        prompts[prompt_key] = len(prompts) + 1
                        archivo_prompt.write(f"INSERT INTO prompt (prompt, proyecto_id) VALUES ('{prompt}', {proyectos[nombre_fichero]});\n")
                    columna += 5
                del df2

                df = pd.read_excel(archivo, sheet_name='AdopcionIA', skiprows=4)
                for index, row in df.iterrows():
                    # Crear sprint si no existe y no está vacío
                    sprint_nombre = row.iloc[2]
                    if not pd.isnull(sprint_nombre) and sprint_nombre != "nan":
                        if (nombre_fichero, sprint_nombre) not in sprints:
                            sprints[(nombre_fichero, sprint_nombre)] = len(sprints)+1
                            archivo_sprint.write(crear_sql_insert("sprint", {"nombre": sprint_nombre, "proyecto_id": proyectos[nombre_fichero]}) + "\n")

                    # Crear tarea si no existe y no está vacío
                    tarea = "" if pd.isnull(row.iloc[3]) else str(row.iloc[3]).replace("'", "").replace("\n", "").replace("\r", "")
                    tarea_descripcion = "" if pd.isnull(row.iloc[4]) else str(row.iloc[4]).replace("'", "").replace("\n", "").replace("\r", "")
                    # if not pd.isnull(tarea):
                    if tarea not in tareas:
                        tareas[tarea] = len(tareas)+1
                        archivo_tarea.write(crear_sql_insert("tarea", {"descripcion": (tarea + ' - ' + tarea_descripcion), "sprint_id": sprints[(nombre_fichero, sprint_nombre)]}) + "\n")

                    # añadir estimación
                    notas = str(row.iloc[41]).replace("'", "").replace("\n", "").replace("\r", "")[:100]
                    owner = str(row.iloc[5]).replace("'", "").replace("\n", "").replace("\r", "")[:40]
                    if (sprint_nombre, tarea, notas, row.iloc[5]) not in estimaciones:
                        estimaciones[(proyectos[nombre_fichero], sprints[(nombre_fichero, sprint_nombre)], tareas[tarea])] = len(estimaciones)+1
                        archivo_estimacion.write(crear_sql_insert("estimacion",
                                                                  {"owner": owner,
                                                                  "proyecto_id": proyectos[nombre_fichero],
                                                                   "sprint_id": sprints[(nombre_fichero, sprint_nombre)],
                                                                   "tarea_id": tareas[tarea],
                                                                   "notas": notas
                                                                   }) + "\n")

                   #Añadir medición por prompt
                    for i in range(6, 44, 5):
                        if row.iloc[i] == 'S':
                            calidad_salida_ia = row.iloc[i + 2] if not pd.isnull(row.iloc[i + 2]) else 0
                            estimacion_sin_ia = row.iloc[i + 3] if not pd.isnull(row.iloc[i + 3]) else 0
                            estimacion_con_ia = row.iloc[i + 4] if not pd.isnull(row.iloc[i + 4]) else 0
                            
                            # Convertir a números enteros
                            if isinstance(estimacion_con_ia, str):
                                estimacion_con_ia = int(estimacion_con_ia) if estimacion_con_ia.isdigit() else 0
                            if isinstance(estimacion_sin_ia, str):
                                estimacion_sin_ia = int(estimacion_sin_ia) if estimacion_sin_ia.isdigit() else 0
                            
                            archivo_medicion.write(crear_sql_insert("medicion_por_prompt", {
                                "calidad_salida_ia": calidad_salida_ia,
                                "estimacion_con_ia": estimacion_con_ia,
                                "estimacion_sin_ia": estimacion_sin_ia,
                                "estimacion_id": estimaciones[(proyectos[nombre_fichero], sprints[(nombre_fichero, sprint_nombre)], tareas[tarea])],
                                "prompt_id": prompts[prompt_key]
                            }) + "\n")


            except Exception as e:
                print(f"Error al leer archivo {archivo}: {e}")
        print(f"-----------------------FIN archivo {nombre_fichero}...")
