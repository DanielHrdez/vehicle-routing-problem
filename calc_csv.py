import pandas as pd
import json
import numpy as np

greedy = pd.read_csv('csv/Greedy.csv')
grasp = pd.read_csv('csv/Grasp.csv')
gvns = pd.read_csv('csv/Gvns.csv')
ITERATIONS = 5
results = {}
greedy_results = []
grasp_results = []
gvns_results = []
for i in range(0, len(greedy), ITERATIONS):
    result_distance = 0
    result_time = 0
    distances = []
    for j in range(ITERATIONS):
        distances.append(greedy.iloc[i + j, 3])
        result_time += greedy.iloc[i + j, 4]
    result_distance = np.mean(distances)
    result_time /= ITERATIONS
    greedy_results.append({
        'Vehiculos': int(greedy.iloc[i, 1]),
        'Distancia Media': round(float(result_distance), 4),
        'Desviacion Estandar': round(np.std(distances), 4),
        'Tiempo Medio': round(float(result_time), 4),
    })
results['Greedy'] = greedy_results
for i in range(0, len(grasp), ITERATIONS):
    result_distance = 0
    result_time = 0
    distances = []
    for j in range(ITERATIONS):
        distances.append(int(grasp.iloc[i + j, 4].split()[2]))
        result_time += grasp.iloc[i + j, 5]
    result_distance = np.mean(distances)
    result_time /= ITERATIONS
    grasp_results.append({
        'Vehiculos': int(grasp.iloc[i, 1]),
        'Candidatos': int(grasp.iloc[i, 2]),
        'Distancia Media': round(float(result_distance), 4),
        'Desviacion Estandar': round(np.std(distances), 4),
        'Tiempo Medio': round(float(result_time), 4),
    })
results['Grasp'] = grasp_results
for i in range(0, len(gvns), ITERATIONS):
    result_distance = 0
    result_time = 0
    distances = []
    for j in range(ITERATIONS):
        distances.append(int(gvns.iloc[i + j, 4].split()[2]))
        result_time += gvns.iloc[i + j, 5]
    result_distance = np.mean(distances)
    result_time /= ITERATIONS
    gvns_results.append({
        'Vehiculos':int( gvns.iloc[i, 1]),
        'Agitaciones': int(gvns.iloc[i, 2]),
        'Distancia Media': round(float(result_distance), 4),
        'Desviacion Estandar': round(np.std(distances), 4),
        'Tiempo Medio': round(float(result_time), 4),
    })
results['Gvns'] = gvns_results
with open('results.json', 'w') as f:
    json.dump(results, f, indent=2)
print('Done')
