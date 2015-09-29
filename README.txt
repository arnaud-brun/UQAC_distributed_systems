APPLICATION SOCKET CLIENT-SERVEUR

Le but de ce projet est de créer une application client-serveur réalisant les tâches suivantes :

- L'utilisateur rentre deux nombres à additionner
- Le serveur fait le calcul et retourne le résultat
- Le résultat est affiché à l'utilisateur
- L'utilisateur peut recommencer tant qu'il le souhaite


Guide d'utilisation :
- Exécuter le serveur (Socket_Serveur.java), qui va confirmer être connecté sur le port 8000
- Exécuter un/plusieurs client (Socket_Client.java) dans une/plusieurs consoles différentes
- Côté serveur, celui-ci va afficher qu'un client s'est bien connecté
- Côté client, l'utilisateur va alors avoir 2 choix : Additionner 2 nombres ou quitter
	- Quitter fermera la connexion du client
	- Additionner lui demandera alors de rentrer 2 nombres, qui seront envoyés au serveur
- Le serveur confirme qu'il a bien reçu l'objet Calc (qui contient les 2 nombres) et la méthode add à exécuter sur cet objet
- Il renvoie le résultat
- Le client affiche alors le résultat à l'utilisateur
- Ce dernier peut alors à nouveau choisir entre additionner et quitter
