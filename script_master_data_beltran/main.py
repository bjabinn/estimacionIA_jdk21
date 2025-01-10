import pandas as pd
import glob

# Obtener lista de archivos .xlsx en el directorio
archivos_xlsx = glob.glob('ficherosExcelOrigen/*.xlsx')

with open('./proyecto.sql', 'w', encoding='utf-8') as archivo_proyecto,\
        open('./sprint.sql', 'w', encoding='utf-8') as archivo_sprint,\
        open('./tarea.sql', 'w', encoding='utf-8') as archivo_tarea,\
        open('./estimacion.sql', 'w', encoding='utf-8') as archivo_estimacion,\
        open('./prompts.sql', 'w', encoding='utf-8') as archivo_prompt:

    proyectos = {}
    sprints = {}
    tareas = []
    estimacion = {}
    prompts = {}

    for archivo in archivos_xlsx:
        df = pd.read_excel(archivo, sheet_name='AdopcionIA', skiprows=5)
        for index, row in df.iterrows():
            equipo = row.iloc[1]
            sprint_nombre = row.iloc[2]
            # tarea_codigo = row.iloc[3] tarea_descripcion = row.iloc[4] Concatenado y añadiendo "-"
            tarea = f"{row.iloc[3]} - {row.iloc[4]}".replace("'", "")
            owner = row.iloc[5]
            notas = str(row.iloc[41]).replace("'", "")
            prompt = row.iloc[44]

            # Crear proyecto si no existe
            if equipo not in proyectos:
                proyectos[equipo] = len(proyectos) + 1
                archivo_proyecto.write(f"INSERT INTO proyecto (id, nombre) VALUES ({proyectos[equipo]}, '{equipo}');\n")

            sprint_key = (equipo, sprint_nombre)
            if sprint_key not in sprints:
                sprints[sprint_key] = len(sprints) + 1
                archivo_sprint.write(f"INSERT INTO sprint (id, nombre, proyecto_id) VALUES ({sprints[sprint_key]}, '{sprint_nombre}', {proyectos[equipo]});\n")

            tarea_id = len(tareas) + 1
            tareas.append(tarea_id)
            archivo_tarea.write(f"INSERT INTO tarea (id, descripcion, sprint_id) VALUES ({tarea_id}, '{tarea}', {sprints[sprint_key]});\n")
            
            # Crear prompt si no existe y no está vacío
            if not pd.isnull(prompt):
                prompt_key = (equipo, prompt)
                if prompt_key not in prompts:
                    prompts[prompt_key] = len(prompts) + 1
                    archivo_prompt.write(f"INSERT INTO prompt (id, prompt, proyecto_id) VALUES ({prompts[prompt_key]}, '{prompt}', {proyectos[equipo]});\n")

            estimacion_key = (sprint_nombre, tarea, notas, owner)
            if estimacion_key not in estimacion:
                estimacion[estimacion_key] = len(estimacion) + 1
                archivo_estimacion.write(f"INSERT INTO estimacion (id, owner, proyecto_id, sprint_id, tarea_id, notas) VALUES ({estimacion[estimacion_key]}, '{owner}', {proyectos[equipo]}, {sprints[sprint_key]}, {tarea_id}, '{notas}');\n")
