Le dossier drunksum peut �tre ouvert comme un projet Android Studio.

Code int�ressant :

    Dans le dossier drunksum/app/src/main/java/ch/hearc/drunksum/

    * DrunkSumActivity.java :
      * onTap : Gestion du clic, d�tection si un �l�ment num�ric est touch� et appel de la m�thode add.
      * add : Ajout du nombre s�lectionn� � la somme.
      * onOptionsItemSelected : Gestion du clic d'un �l�ment du menu (r�initialissation et division).

    * OcrDetectorProcessor.java :
      * receiveDetections : m�thode appell�e par l'API Vision lors de la d�tection. La m�thodes re�oit une collection de paragraphes, qui contiennent des lignes. Chaque ligne est trait�e pour d�finir si elle contient un nombre ou non.

    Dans le dossier drunksum/app/src/test/java/ch/hearc/drunksum/

    * NumberPatternTest.java
      * Tests unitaires pour valider l'expression r�guli�re.