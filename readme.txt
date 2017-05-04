Le dossier drunksum peut être ouvert comme un projet Android Studio.

Code intéressant :

    Dans le dossier drunksum/app/src/main/java/ch/hearc/drunksum/

    * DrunkSumActivity.java :
      * onTap : Gestion du clic, détection si un élément numéric est touché et appel de la méthode add.
      * add : Ajout du nombre sélectionné à la somme.
      * onOptionsItemSelected : Gestion du clic d'un élément du menu (réinitialissation et division).

    * OcrDetectorProcessor.java :
      * receiveDetections : méthode appellée par l'API Vision lors de la détection. La méthodes reçoit une collection de paragraphes, qui contiennent des lignes. Chaque ligne est traitée pour définir si elle contient un nombre ou non.

    Dans le dossier drunksum/app/src/test/java/ch/hearc/drunksum/

    * NumberPatternTest.java
      * Tests unitaires pour valider l'expression régulière.