#!/usr/bin/python
# -*- coding: utf-8 -*-

import json

with open('enviroments.json') as json_data:
	d = json.load(json_data)
	print "Ambientes Disponiveis : " 
	my_envs = d["enviroments"]

	for x in range(0,len(my_envs)) :
		print str(x) + " - " + str(my_envs[x]["env"])


	mode = int(raw_input("\n\nSelecione um dos ambientes Disponiveis(0-"+str(len(my_envs) - 1)+") : "))
	print "Serão utilizados os dados de configuração do ambiente de " + my_envs[mode]["env"]