import pandas as pd
import glob
import warnings

# Ignorar el aviso de la biblioteca openpyxl
warnings.filterwarnings("ignore")

# Obtener lista de archivos .xlsx en el directorio
archivos_xlsx = glob.glob('ficherosExcelOrigen/*.xlsx')

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
    valores = ", ".join(f"'{valor}'" for valor in datos.values())
    sql += f"{columnas}) VALUES ({valores});\n"
    return sql

# Código principal
with open('./proyecto.sql', 'w', encoding='utf-8') as archivo_proyecto,\
        open('./sprint.sql', 'w', encoding='utf-8') as archivo_sprint,\
        open('./tarea.sql', 'w', encoding='utf-8') as archivo_tarea,\
        open('./prompts.sql', 'w', encoding='utf-8') as archivo_prompt,\
        open('./estimacion.sql', 'w', encoding='utf-8') as archivo_estimacion:

    for archivo in archivos_xlsx:
        try:
            df = pd.read_excel(archivo, sheet_name='AdopcionIA', skiprows=4)
            for index, row in df.iterrows():
                equipo = row.iloc[1]
                if not pd.isnull(equipo) and equipo != "" and equipo != "nan":
                    if equipo not in proyectos:
                        proyectos[equipo] = len(proyectos)+1
                        archivo_proyecto.write(f"INSERT INTO proyecto (nombre) VALUES ('{equipo}');\n")

                # Crear sprint si no existe y no está vacío
                sprint_nombre = row.iloc[2]
                if not pd.isnull(sprint_nombre) and sprint_nombre != "nan":
                    if (equipo, sprint_nombre) not in sprints:
                        sprints[(equipo, sprint_nombre)] = len(sprints)+1
                        archivo_sprint.write(crear_sql_insert("sprint", {"nombre": sprint_nombre, "proyecto_id": proyectos[equipo]}) + "\n")

                # Crear tarea si no existe y no está vacío
                tarea_codigo = str(row.iloc[3])
                tarea_descripcion = row.iloc[4]
                tarea = tarea_codigo
                if not pd.isnull(tarea_descripcion) and tarea_descripcion != "":
                    tarea += f" - {tarea_descripcion}".replace("'", "")
                if tarea != "nan":
                    if tarea not in tareas:
                        tareas[tarea] = len(tareas)+1
                        archivo_tarea.write(crear_sql_insert("tarea", {"descripcion": tarea, "sprint_id": sprints[(equipo, sprint_nombre)]}) + "\n")

                # Crear prompt si no existe y no está vacío
                prompt = row.iloc[44]
                if prompt != "nan":
                    prompt_key = (proyectos[equipo], prompt)
                    if prompt_key not in prompts:
                        prompts[prompt_key] = len(prompts)+1
                        archivo_prompt.write(f"INSERT INTO prompt (prompt, proyecto_id) VALUES ('{prompt}', {proyectos[equipo]});\n")

                # Crear estimación si no existe y no está vacío
                notas = str(row.iloc[41]).replace("'", "")
                if notas != "nan":
                    if (sprint_nombre, tarea, notas, row.iloc[5]) not in estimaciones:
                        estimaciones[(sprint_nombre, tarea, notas, row.iloc[5])] = len(estimaciones)
                        archivo_estimacion.write(crear_sql_insert("estimacion", {"owner": row.iloc[5], "proyecto_id": proyectos[equipo], "sprint_id": sprints[(equipo, sprint_nombre)], "tarea_id": tareas[tarea], "notas": notas}) + "\n")
        except Exception as e:
            print(f"Error al leer archivo {archivo}: {e}")
