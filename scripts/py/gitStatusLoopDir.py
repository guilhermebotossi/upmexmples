import os, sys

root = sys.argv[1]
folderStartsWith = sys.argv[2]

for item in os.listdir(root):
	if(item.startswith(folderStartsWith)):
		curDir = os.path.join(root,item)
		command = "git --git-dir="+curDir+"\.git --work-tree="+curDir+ " status"
		print (command)
		os.system(command)
