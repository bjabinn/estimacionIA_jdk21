import pandas as pd
import os
import warnings
from openpyxl import load_workbook

# Ignora todas las advertencias de la biblioteca openpyxl
warnings.filterwarnings("ignore", module='openpyxl')

# Ruta a la carpeta que contiene los archivos Excel
ruta_excel = '.'

# Ruta al archivo de salida .sql
ruta_salida = './actualizar.sql'

# Abre el archivo de salida .sql en modo escritura
with open(ruta_salida, 'w', encoding="utf-8") as archivo_salida:

    # Recorre la carpeta que contiene los archivos Excel
    for archivo in os.listdir(ruta_excel):
        if archivo.endswith('.xlsx') or archivo.endswith('.xls'):
            
            # Abre el archivo Excel utilizando openpyxl
            wb = load_workbook(os.path.join(ruta_excel, archivo))
            ws = wb.active
            
            # Lee el archivo Excel utilizando pandas
            df = pd.read_excel(os.path.join(ruta_excel, archivo), header=4)
            
            # Selecciona la columna que deseas
            owner = df[ws["F5"].value].drop_duplicates()
            proyecto = df[ws["B5"].value].drop_duplicates()
            sprint = df[ws["C5"].value].drop_duplicates()
            historia_jira = df[ws["D5"].value]
            descripcion = df[ws["E5"].value]

            # Elimina filas vacías
            df = df.dropna(how='all')

            # Inserta los datos en el archivo de salida .sql
            archivo_salida.write('''
                INSERT INTO estimacion (owner) VALUES {};
                INSERT INTO proyecto (nombre) VALUES {};
                INSERT INTO sprint (nombre) VALUES {};
            '''.format(tuple(owner), tuple(proyecto), tuple(sprint)))

            # Genera un solo INSERT con todos los valores de historia_jira concatenados con descripcion
            valores = [f"('{historia_jira.iloc[index]} - {descripcion.iloc[index]}')" for index, row in df.iterrows()]
            archivo_salida.write('''
                INSERT INTO tarea (descripcion) VALUES {};
            '''.format(', '.join(valores)))
            archivo_salida.write('\n')
