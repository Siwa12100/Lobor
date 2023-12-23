# Réflexion sur la conception du plugin Lobor

Le but est d'avoir un système de montures qui permettent un déplacement plus rapide qu'à pied sur l'Agora.

### Monture
Pour commencer, il faut donc une classe monture qui aura la responsabilité de gérer l'entité sur laquel on monte.
Voilà ce qu'une monture doit pouvoir faire :

* apparaitre
* disparaitre
* s'immobiliser / (re)pouvoir avancer
* modifier sa vitesse
* faire automatiquement monter son propriétaire dessus

### Invocateur de montures
Il faut ensuite un moyen de qu'on joueur créée une monture.
Voilà ce qu'un invocateur de montures doit pouvoir faire :

* Créer une monture
* Indiquer à la mnture

### MontureManager
Il faut ensuite pouvoir gérer l'ensemble des montures qui existent sur le serveur.