Generation du terrain par un fichier xml
	--> environment taille de la grid
	--> spawner entit� qui va creer les agents au meme endroit a interval r�gulier 5s
	--> wall zone ou le lemmings peut marcher les walls d�limitant la zone de jeu sont cr��s dynamiquement

decision de l'agent
	deux degr�es de libert� pour ces mouvements est/ouest, si un objet se trouve sur la case a cot� alors la direction est bloqu�e
	si dans son champs de perception (2 cases devant lui) il trouve l'entity endarea il s'y dirige
	si il y a un trou, modification de la position du corps t-->tx(decision de l'agent move du corps)
								    tf(modification du corps si la case endessous en libre)

ps : il n'y a pas de croisement de lemmings puisque l'environnement est une grid et que une case = un objet.
ps2 : un lemmings doit mourir qd ses degr�es de libert� sont null --> plus de move possible

TODO : 
	am�liorer la perception
	mettre en place l'algo d'apprentissage (chaque d�cision de l'agent = un noeud dans un arbre, si le chemin parcouru n'est pas bon; dead de l'agent; les agents suivant ne doivent pas reproduire enti�rement le meme chemin)
	mettre en place l'interface du jeu (menu, creation des niveau)
	ameliorer la prise de d�cision, impl�mentation du saut pour des trous par ex
	   	