import pandas as pd

df = pd.read_excel('ficherosExcelOrigen/DED-NAUA_Plantilla_Adopcion IA.xlsx', sheet_name='AdopcionIA', skiprows=5)

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
    medicion_prompts = []

    for index, row in df.iterrows():
        equipo = row.iloc[1]
        sprint_nombre = row.iloc[2]
        # tarea_codigo = row.iloc[3] tarea_descripcion = row.iloc[4] Concatenado y añadiendo "-"
        tarea = f"{row.iloc[3]} - {row.iloc[4]}"
        owner = row.iloc[5]
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
        
        estimacion_key = (sprint_key, tarea_id, owner)
        if estimacion_key not in estimacion:
            estimacion[estimacion_key] = len(estimacion) + 1
            archivo_estimacion.write(f"INSERT INTO estimacion (id, owner, proyecto_id, sprint_id, tarea_id) VALUES ({estimacion[estimacion_key]}, '{owner}', {proyectos[equipo]}, {sprints[sprint_key]}, {tarea_id};\n")

        # Crear prompt si no existe
        if prompt not in prompts and not pd.isnull(prompt):
            prompts[prompt] = len(prompts) + 1
            archivo_prompt.write(f"INSERT INTO prompt (id, nombre) VALUES ({prompts[prompt]}, '{prompt}');\n")

        