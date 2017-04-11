import json

with open('enviroments.json') as json_data:
	d = json.load(json_data)
	print "Ambientes Disponiveis : " 

	for x in d["enviroments"] :
		print x["env"]