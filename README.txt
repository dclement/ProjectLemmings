Generation du terrain par un fichier xml
	--> environment taille de la grid
	--> spawner entité qui va creer les agents au meme endroit a interval régulier 5s
	--> wall zone ou le lemmings peut marcher les walls délimitant la zone de jeu sont créés dynamiquement

decision de l'agent
	deux degrées de liberté pour ces mouvements est/ouest, si un objet se trouve sur la case a coté alors la direction est bloquée
	si dans son champs de perception (2 cases devant lui) il trouve l'entity endarea il s'y dirige
	si il y a un trou, modification de la position du corps t-->tx(decision de l'agent move du corps)
								    tf(modification du corps si la case endessous en libre)

ps : il n'y a pas de croisement de lemmings puisque l'environnement est une grid et que une case = un objet.
ps2 : un lemmings doit mourir qd ses degrées de liberté sont null --> plus de move possible

TODO : 
	améliorer la perception
	mettre en place l'algo d'apprentissage (chaque décision de l'agent = un noeud dans un arbre, si le chemin parcouru n'est pas bon; dead de l'agent; les agents suivant ne doivent pas reproduire entièrement le meme chemin)
	mettre en place l'interface du jeu (menu, creation des niveau)
	ameliorer la prise de décision, implémentation du saut pour des trous par ex
	   	