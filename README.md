# connection à postgresql depuis Android

activity main : si l'utilisateur est deja connecté avec sharedPreferences, alors direction vers l'activité menu
Sinon, direction vers l'activité d'enregistrement de l'utilisateur. 
Collecte du numéro de licence, mail et nom. 
Si non vides, et si unicité par rapport à la table utilisateur, 
Alors insertion des données dans postgresql
