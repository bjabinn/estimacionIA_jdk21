import pandas as pd

df = pd.read_excel('ficherosExcelOrigen/DED-NAUA_Plantilla_Adopcion IA.xlsx', sheet_name='AdopcionIA', skiprows=5)

with open('sqlSalida/proyecto.sql', 'w') as archivo_proyecto, \
        open('sqlSalida/sprint.sql', 'w') as archivo_sprint,\
        open('sqlSalida/tarea.sql', 'w') as archivo_tarea:

    proyectos = {}
    sprints = {}
    tareas = []

    for index, row in df.iterrows():
        equipo = row.iloc[1]
        sprint_nombre = row.iloc[3]
        tarea_codigo = row.iloc[4]
        tarea_descripcion = row.iloc[5]

        # Crear proyecto si no existe
        if equipo not in proyectos:
            proyectos[equipo] = len(proyectos) + 1
            archivo_proyecto.write(f"INSERT INTO Proyecto (id, nombre) VALUES ({proyectos[equipo]}, '{equipo}');\n")

        sprint_key = (equipo, sprint_nombre)
        if sprint_key not in sprints:
            sprints[sprint_key] = len(sprints) + 1
            archivo_sprint.write(f"INSERT INTO Sprint (id, nombre, proyecto_id) VALUES ({sprints[sprint_key]}, '{sprint_nombre}', {proyectos[equipo]});\n")

        tarea_id = len(tareas) + 1
        tareas.append(tarea_id)
        archivo_tarea.write(f"INSERT INTO Tarea (id, descripcion, sprint_id) VALUES ({tarea_id}, '{tarea_descripcion}', {sprints[sprint_key]});\n")
