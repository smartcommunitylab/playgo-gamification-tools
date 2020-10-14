import json

# I template sono stati creati a partire dalle visualizzazioni e dashboard del 2017
# Se necessario ricostruire il file template_mapping_id.csv, scaricare da kibana tutte le visualizzazioni Play&Go 2017
# e utilizzare questo script per ricreare il template di mapping


json_file = open('visualizzazioni 2017 corretta.json','r')
out = open('template_mapping_id.csv','w')
data = json.load(json_file)
for element in data:
  csv_row = element['_id'] + "," + element['_source']['title']
  print(csv_row)
  out.write(csv_row+'\n')
