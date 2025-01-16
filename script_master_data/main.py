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


# Código principal
with open('./proyecto.sql', 'w', encoding='utf-8') as archivo_proyecto,\
        open('./sprint.sql', 'w', encoding='utf-8') as archivo_sprint,\
        open('./tarea.sql', 'w', encoding='utf-8') as archivo_tarea,\
        open('./prompts.sql', 'w', encoding='utf-8') as archivo_prompt,\
        open('./estimacion.sql', 'w', encoding='utf-8') as archivo_estimacion:

    for archivo in archivos_xlsx:
            df = pd.read_excel(archivo, sheet_name='AdopcionIA', skiprows=4)
            valores_unicos = []
            for index, row in df.iterrows():
                equipo = row.iloc[1]
                if not pd.isnull(equipo) and equipo != "":
                    if equipo not in proyectos:
                        proyectos[equipo] = len(proyectos)+1
                        archivo_proyecto.write(f"INSERT INTO proyecto (nombre) VALUES ('{equipo}');\n")

                # Crear sprint si no existe y no está vacío
                sprint_nombre = row.iloc[2]
                if not pd.isnull(sprint_nombre) and sprint_nombre != '':
                    sprint_key = (proyectos[equipo], sprint_nombre)
                    if sprint_nombre not in sprints:
                        sprints[sprint_key] = len(sprints)+1
                        archivo_sprint.write(f"INSERT INTO sprint (nombre, proyecto_id) VALUES ('{sprint_nombre}', '{proyectos[equipo]}');\n")

                # Crear tarea si no existe y no está vacío
                tarea_codigo = str(row.iloc[3])
                tarea_descripcion = row.iloc[4]
                tarea = tarea_codigo
                if not pd.isnull(tarea_descripcion) and tarea_descripcion != "":
                    tarea += f" - {tarea_descripcion}".replace("'", "")
                if not pd.isnull(tarea) and tarea != "":
                    if tarea not in tareas:
                        tareas[tarea] = len(tareas)+1
                        archivo_tarea.write(f"INSERT INTO tarea (descripcion, sprint_id) VALUES ('{tarea}', {sprints[sprint_key]});\n")

                # Crear prompt si no existe y no está vacío
                prompt = row.iloc[44]
                valores_unicos = df.iloc[:, 1].unique()
                for valor in valores_unicos:
                    if not pd.isnull(prompt) and prompt != "" and prompt != "nan":
                        prompt_key = (proyectos[equipo], prompt)
                        if prompt_key not in prompts:
                            prompts[prompt_key] = len(prompts)+1
                            archivo_prompt.write(f"INSERT INTO prompt (prompt, proyecto_id) VALUES ('{prompt}', {proyectos[equipo]});\n")

                # Crear estimación si no existe y no está vacío
                notas = str(row.iloc[41]).replace("'", "")
                owner = row.iloc[5]
                if not pd.isnull(prompt) and notas != "nan":
                    estimacion_key = (proyectos[equipo], sprints[sprint_key], tareas[tarea], owner, notas)
                    if estimacion_key not in estimaciones:
                        estimaciones[estimacion_key] = len(estimaciones)
                        archivo_estimacion.write(f"INSERT INTO estimacion (notas, owner, proyecto_id, sprint_id, tarea_id) VALUES ('{notas}', '{owner}', {proyectos[equipo]}, {sprints[sprint_key], {tareas[tarea]}});\n")
        
