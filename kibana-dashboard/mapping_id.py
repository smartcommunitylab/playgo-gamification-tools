import json

# 
# Script per iniettare gli id delle visualizzazioni del nuovo anno al posto di quelle presenti nel template di dashboard
#


mapping_template = open('template_mapping_id.csv','r')
dashboard_file = open('../dashboard_2020_ferrara/playAndGo dashboard 2020 Ferrara.json','r')
visualizations_file = open('../dashboard_2020_ferrara/visualizzazioni 2020 Ferrara con id.json','r')
dashboard_out = open('../dashboard_2020_ferrara/dashboard.mapped.2020-ferrara.json','w')

# load template mapping ids
mappings = [ mapping.split(',') for mapping in mapping_template]

dashboard_content = dashboard_file.read()

visualizations = json.load(visualizations_file)
for visualization in visualizations:
  title = visualization["_source"]["title"]
  for mapping in mappings:
    if title.split(':')[1] in mapping[1]:
      dashboard_content = dashboard_content.replace(mapping[0], visualization["_id"])
      print('replaced ' + mapping[0] + ' with ' + visualization["_id"])

dashboard_out.write(dashboard_content)
print("Created dashboard schema")
