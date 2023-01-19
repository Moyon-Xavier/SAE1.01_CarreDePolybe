class SAEpolybe_RADENNE_VASSET_MOYON extends Program {
		
    final int LARGEUR = 5;  // taille du carré (5x5 dans notre cas)

    //////////////////////////////////////////////////////////////////////////    

    // La fonction String initialiserCarre() retourne une chaine de caractères contenant le carré de Polybe (version de base, sans clé, c'est-à-dire la chaine "ABCDEFGHIJKLMNOPQRSTUVXYZ", le V et le W sont "fusionnés" en V)
    String initialiserCarreSimple(){
        String alphabet="";
        char lettre;
        for(int i = 0 ; i<26 ; i=i+1){//ou alors plutot que de faire une boucle on ecrit seulement : return "abcdefghijklmnopqrstuvxyz"

            lettre=(char)('A'+i);
            if (lettre != 'W'){//on verifie que la lettre est differente de w  
                alphabet=alphabet+lettre;//on rajoutre a notre carre la lettre si elle differente de w
            }
        }
        return alphabet;
    }

    void testInitialiserCarreSimple(){
        assertEquals("ABCDEFGHIJKLMNOPQRSTUVXYZ",initialiserCarreSimple());
        assertNotEquals("BACDEFGHIJKLMNOPQRSTUVXYZ",initialiserCarreSimple());
    }

    //////////////////////////////////////////////////////////////////////////

    // La fonction void afficherCarre(String carre) affiche le carré de Polybe carrz passé en paramètre comme illustré dans l'exemple ci-après.
    // Par exemple : si le carré passé en paramètre est : "ABCDEFGHIJKLMNOPQRSTUVXYZ", la fonction devra afficher : 
    //  |0 1 2 3 4
    // ------------
    // 0|A B C D E
    // 1|F G H I J
    // 2|K L M N O 
    // 3|P Q R S T 
    // 4|U V X Y Z

    // NB : On considère dans cette fonction que le carré passé en paramètre est valide (càd constitué de 25 lettres distinctes en majuscule, le W se substituant en V)

    void afficherCarre(String carre){
        print(" |");//on fait la barre avant les index des colonnes 
        for (int i = 0 ; i < LARGEUR; i=i+1){
            print(i + " ");//on affiche les index des colonnes en fonction du nombre de colonne que l'on souhaite ici longueur
        }
        println();
        for (int i = 0 ; i < LARGEUR+1 ; i=i+1){
            print("--");//on affiche une ligne de tirets en fonction du nombre de colonne que l'on souhaite ici longueur+1 car il faut prendre en compte la colonne des index
        }

        println();
        for (int i = 0 ; i < LARGEUR ; i=i+1){
            print(i+ "|");//ici on affiche les index des lignes en fonction du nombre de lignes que l'on souhaite ici largeur suivi de la barre de separation
            for (int z = 0 ;z<LARGEUR;z=z+1){//ici on affiche les lettres du carre en fonction du nombre de colonne
                print(charAt(carre,((i*LARGEUR)+z))+ " ");
            }
            println();
        }
        println();

    }
    

    //////////////////////////////////////////////////////////////////////////

    // La fonction String coderLettre (String carre, char lettre) retourne une chaîne de 2 caractères (2 entiers entre 0 inclus et LARGEUR exclus) contenant l'encodage du caractère lettre passé en paramètre en considérant le carré de Polybe carre également passé en paramètre.
    // Par exemple :
    // si on considère le carré de Polybe sans clé (càd le carré ABCDE représenté par la chaine "ABCDEFGHIJKLMNOPQRSTUVXYZ") : 
    //                                                           FGHIJ
    //                                                           KLMNO                   
    //                                                           PQRST 
    //                                                           UVWYZ
    //      'A' est codé "00"
    //      'B' est codé "01"
    //      'F' est codé "10"
    //      'V' est codé "41"
    //      'W' est codé "41"
    //      'Z' est codé "44"
    //      'P' est codé "30"

    // si on considère le carré de Polybe donné par la chaine "AZERTYUIOPQSDFGHJKLMXCVBN" :
    //      'A' est codé "00"
    //      'B' est codé "43"
    //      'Z' est codé "01"

    // NB : On considère dans cette fonction que le carré passé en paramètre est valide (càd constitué de 25 lettres distinctes en majuscule, le W se substituant en V)

    // Indication : pensez à la division entière et au modulo

    String coderLettre (String carre, char lettre){
        int numLigne=0;
        int numColone=0;
        char CNumLigne='0';
        char CNumColone='0';//ici on initialise 4 fonctions, 2 pour avoir les numeros des colonnes et des lignes en int  pour faciliter les test et la lecture et 2 autres pour faciliter
	//la concatenation des numero pour avoir le code
        String code ="";//on initialise le code a une chaine vides
        int taille=length(carre);
        boolean estPresent=false;
	if (lettre=='W'){//on verifie que la lettre est egale a w si oui on la transforme en v
		lettre='V';
	}
        while((!estPresent)&&((LARGEUR*numLigne)+numColone<taille)){//on utilise une boucle while qui tourne tant que l'on a pas trouve la lettre et que l'on a pas teste tout le carre 

            if (charAt(carre,(LARGEUR*numLigne)+numColone)==lettre){//on verifie si le la lettre est egal a la lettre actuellement selectionne
                estPresent=true;

            }else if (numColone==LARGEUR-1){//on verifie que nous sommes pas en bout de lignes si c'est le cas on on change de colone et de lignes 

                numColone=0;
                CNumColone='0';
                numLigne=numLigne+1;
                CNumLigne=(char)(CNumLigne+1);
            }
            else{//sinon on passe a la colonne suivante
                numColone=numColone+1;
                CNumColone=(char)(CNumColone+1);
            }
        }
        if (estPresent){//si la lettre est presente on inscrit le code
            code=code+CNumLigne+CNumColone;
        }
        return code;
    }

void testCoderLettre(){
        assertEquals(coderLettre("ABCDEFGHIJKLMNOPQRSTUVXYZ",'A'),"00");
        assertEquals(coderLettre("ABCDEFGHIJKLMNOPQRSTUVXYZ",'Z'),"44");
        assertEquals(coderLettre("ABCDEFGHIJKLMNOPQRSTUVXYZ",'C'),"02");
        assertNotEquals(coderLettre("ABCDEFGHIJKLMNOPQRSTUVXYZ",'Z'),"55");
        assertEquals(coderLettre("ABCDEFGHIJKLMNOPQRSTUVXYZ",'W'),"41");
    }
    //////////////////////////////////////////////////////////////////////////

    // La fonction String coderMessage (String carre, String message) retourne une chaîne de caractères contenant l'encodage de la chaîne de caractères message passé en paramètre avec le carré de Polybe carre donné en paramètre.
    // Chaque paire d'entiers (compris entre 0 et 4) correspondant à chaque lettre sera séparée de la suivante par un espace.
    // Pensez à utiliser la fonction coderLettre.
    // Par exemple, si le carré considéré est celui sans clé ("ABCDEFGHIJKLMNOPQRSTUVXYZ") et le message à coder est "BONJOUR" alors le résultat attendu est "01 24 23 14 24 40 32 "

    // NB : On considère dans cette fonction que le carré passé en paramètre est valide (càd constitué de 25 lettres distinctes en Smajuscule, le W se substituant en V)
    // NB : On considère dans cette fonction que le message passé en paramètre est valide (càd constitué uniquement des 26 lettres de l'alphabet en majuscule)
///////////////////////CODER MESSAGE//////////////////////////////////////////////////////
    String coderMessage(String carre, String message){
        String messageCode="";
        String lettreCode;
        for(int i = 0; i< length(message); i=i+1){//ici on appele la fonction coderLettre autant de fois qu'il y a de lettres dans le messages a la fin on rajoute un  espace afin qu'il
	//corresponde a la norme que nous nous sommes donne
            lettreCode = coderLettre(carre,charAt(message,i));
            messageCode=messageCode+lettreCode+" ";
        }
        

        return messageCode;
    }

    void testCoderMessage(){
        assertEquals(coderMessage("ABCDEFGHIJKLMNOPQRSTUVXYZ","BUTINFORMATIQUE"),"01 40 34 13 23 10 24 32 22 00 34 13 31 40 04 ");
        assertEquals(coderMessage("ABCDEFGHIJKLMNOPQRSTUVXYZ","AEKOUZ"),"00 04 20 24 40 44 ");
        assertNotEquals(coderMessage("ABCDEFGHIJKLMNOPQRSTUVXYZ","AEKOUZ"),"00 04 20 24 40 44");
    }
    
    //////////////////////////////////////////////////////////////////////////

    // La fonction String decoderMessage (String carre, String messageCode) retourne une chaîne de caractères contenant le décodage de la chaîne de caractère messageCode avec le carré de Polybe carre donné en paramètre.
    // Par exemple, si carre = "ABCDEFGHIJKLMNOPQRSTUVXYZ" et messageCode = "01 24 23 14 24 40 32 " alors le résultat attendu est "BONJOUR"

    // NB : On considère dans cette fonction que le carré passé en paramètre est valide (càd constitué de 25 lettres distinctes en majuscule, le W se substituant en V)
    // NB : On considère dans cette fonction que le message codé passé en paramètre est valide (càd constitué de paires d'entiers compris entre 0 et LARGEUR-1 inclus et séparées par un espace)

//////////////////////////DECODER LETTRE MESSAGE//////////////////////////////////////////

    char decoderLettre(String carre , String code){
        int ligne=(int)(charAt(code,0)-'0');//ici on converti le code en String en code en int avec 2 variables
        int colonne=(int)(charAt(code,1)-'0');
        return charAt(carre,((ligne*LARGEUR)+colonne));//ici on utilise les chiffres afin de connaitre sa position dans le carre
    }

    String decoderMessage(String carre, String messageCode){//ici on utilise le meme principe que pour la fonction coderMessage mais un de maniere differente
        String message="";
        int tailleMessageCode = length(messageCode);
        for (int i = 0 ; i < tailleMessageCode; i=i+3){//ici on incremente de 3 notre compteur car entre chaque lettre est code en 2 chiffres suivi d'un espace soit 3 caracteres ce qui 
	//explique le 3
            message=message+decoderLettre(carre,substring(messageCode,i,i+2));//ensuite on utilise un subString() car chaque lettre est code en 2 chiffres
        }
        return message;
    }

    void testDecoderLettre(){
        assertEquals(decoderLettre("ABCDEFGHIJKLMNOPQRSTUVXYZ","00"),'A');
        assertEquals(decoderLettre("ABCDEFGHIJKLMNOPQRSTUVXYZ","04"),'E');
        assertEquals(decoderLettre("ABCDEFGHIJKLMNOPQRSTUVXYZ","40"),'U');
        assertEquals(decoderLettre("ABCDEFGHIJKLMNOPQRSTUVXYZ","41"),'V');
        assertEquals(decoderLettre("ABCDEFGHIJKLMNOPQRSTUVXYZ","44"),'Z');
        assertEquals(decoderLettre("ABCDEFGHIJKLMNOPQRSTUVXYZ","32"),'R');

        assertEquals(decoderLettre("BACDEFGHIJKLMNOPQRSTUVXYZ","00"),'B');
        
    }

    void testDecoderMessage(){
        assertEquals(decoderMessage("ABCDEFGHIJKLMNOPQRSTUVXYZ","01 40 34 13 23 10 24 32 22 00 34 13 31 40 04 "),"BUTINFORMATIQUE");
        assertEquals(decoderMessage("ABCDEFGHIJKLMNOPQRSTUVXYZ","00 04 20 24 40 44 "),"AEKOUZ");
        
    }




    //////////////////////////////////////////////////////////////////////////

    // La fonction boolean estPresent(String mot, char lettre) retourne True si le caractère lettre est dans mot, faux sinon.
    // Par exemple :
    //      si mot = "BONJOUR" et lettre = 'B' alors le résultat de la fonction est True
    //      si mot = "BONJOUR" et lettre = 'R' alors le résultat de la fonction est True
    //      si mot = "BONJOUR" et lettre = 'M' alors le résultat de la fonction est False

//////////////////////////EST PRESENT//////////////////////////////////////////
    boolean estPresent(String mot, char lettre){//On parcours le mot lettre par lettre et on verifie si la lettre est egale que l'on cherche est egal a la lettre selectionne
        int taillech=length(mot);
        boolean present=false;
	int cpt=0;
	while((!present)&& (cpt<taillech)){
            if (charAt(mot,cpt)==lettre){
                present=true;
            }
	    cpt=cpt+1;
        }
        return present;

    }

    void testEstPresent(){
        assertTrue(estPresent("BONJOUR",'B'));
        assertTrue(estPresent("BUTINFO",'I'));
        assertTrue(estPresent("BONJOUR",'O'));
        assertFalse(estPresent("BONJOUR",'M'));
        assertFalse(estPresent("BONJOUR",'0'));
        assertFalse(estPresent("SAE",'O'));
        assertFalse(estPresent("IUTA",'M'));

    }

//////////////////////////Carre Avec Cle//////////////////////////////////////

    //////////////////////////////////////////////////////////////////////////

    // La fonction String initialiserCarreAvecCle(String cle) retourne une chaine de caractères contenant le carré de Polybe amélioré en considérant la clé passée en paramètre.
    // Pensez à utiliser la fonction estPresent
    // Par exemple, si cle = "BONJOUR" alors le résultat attendu est : "BONJURACDEFGHIKLMPQSTVXYZ"
    // Par exemple, si cle = "BUTINFORMATIQUE" alors le résultat attendu est : "BUTINFORMAQECDGHJKLPSVXYZ"

    // NB : On considère dans cette fonction que la clé passée en paramètre est valide (càd constituée uniquement de lettres de l'alphabet en majuscule, le W se substituera en V)


    String initialiserCarreAvecCle(String cle){
        char valeurIntermediaire;
        int tailleCle=length(cle);
        String carre="";
        for (int i=0; i<tailleCle; i=i+1){//ici on traite la cle
            valeurIntermediaire=charAt(cle,i);
            if (!(estPresent(carre,valeurIntermediaire))&&(valeurIntermediaire!='W')){//ici on verifie si on il y a des lettres en doubles dans la cle (si oui on l'enleve)et on verifie 
	    //qu'il n'y ait pas de W (si oui on l'enleve)
                carre=carre+valeurIntermediaire;
            }
        }
        for(int i = 0;i<26;i=i+1){//ici on ajoute les lettres qui restent au carre
	//On ajoute ensuite chaque lettre de l'alphabet en verifiant qu'elle soit differente de W et non presentes sur le carre
            valeurIntermediaire=(char)('A'+i);
            if ((valeurIntermediaire!='W')&&(!estPresent(carre,valeurIntermediaire))){//ici on verifie que
                carre=carre+valeurIntermediaire;
            }
        }
        return carre;  
    }

    void testInitialiserCarreAvecCle(){
        assertEquals("BUTINFORMAQECDGHJKLPSVXYZ",initialiserCarreAvecCle("BUTINFORMATIQUE"));
        assertEquals("ABCDEFGHIJKLMNOPQRSTUVXYZ",initialiserCarreAvecCle(""));
        assertEquals("ABCDEFGHIJKLMNOPQRSTUVXYZ",initialiserCarreAvecCle("W"));
        assertEquals("AGONBCDEFHIJKLMPQRSTUVXYZ",initialiserCarreAvecCle("WAGON"));
        assertEquals("GROUPEABCDFHIJKLMNQSTVXYZ",initialiserCarreAvecCle("GROUPEE"));
    }


    //////////////////////////////////////////////////////////////////////////
    // LES FONCTIONS QUI SUIVENT SONT DES FONCTIONS DE VERIFICATION DE SAISIE
    //////////////////////////////////////////////////////////////////////////

    //////////////////////////////////////////////////////////////////////////

    // La fonction boolean estLettreMajuscule(char c) vérifie le caractère passé en paramètre est une lettre de l'alphabet en majuscule
    // Par exemple :
    //  si c='b', la fonction retourne false
    //  si c='B', la fonction retourne true

//////////////////////////Est Lettre Majuscule//////////////////////////////////////

    boolean estLettreMajuscule(char c){
        return ((c>='A')&&(c<='Z'));
    }

    void testLettreMajuscule(){
        assertTrue(estLettreMajuscule('A'));
        assertTrue(estLettreMajuscule('Z'));
        assertTrue(estLettreMajuscule('B'));
        assertTrue(estLettreMajuscule('V'));

        assertFalse(estLettreMajuscule('a'));
        assertFalse(estLettreMajuscule('z'));
        assertFalse(estLettreMajuscule('b'));
        assertFalse(estLettreMajuscule('v'));

        assertFalse(estLettreMajuscule(' '));
        assertFalse(estLettreMajuscule('!'));
        
        assertFalse(estLettreMajuscule('0'));
        assertFalse(estLettreMajuscule('9'));
        
    }

    //////////////////////////////////////////////////////////////////////////

    // La fonction estCleValide vérifie que la clé passée en paramètre est valide (càd constituée uniquement de lettres de l'alphabet en majuscule)
    // Par exemple :
    //  si cle="BUTINFORMATIQUE", la fonction retourne true
    //  si cle="BUTINF ORMATIQUE", la fonction retourne false
    //  si cle="BUTINFORMATIQUE!", la fonction retourne false
    //  si cle="ButInformatique", la fonction retourne false

//////////////////////////EST CLE VALIDE//////////////////////////////////////

    boolean estCleValide(String cle){
        boolean valide = true;
        int taillecle=length(cle);
        int i = 0;
        char lettre;
        while ((valide)&&(i<taillecle)){
            lettre=charAt(cle,i);
            if ((!estLettreMajuscule(lettre))||(lettre=='W')){//Ici on a rajoute le fait que la cle ne contienne pas de w
	    //On verifie que la cle ne contienne pas non plus de majuscule
                valide=false;
            }
            i=i+1;
        }
        return valide;
    }

    void testEstCleValide(){
        assertTrue(estCleValide("BUTINFORMATIQUE"));
        assertFalse(estCleValide("BUTINF ORMATIQUE"));
        assertFalse(estCleValide("BUTINFORMATIQUE!"));
        assertFalse(estCleValide("ButInformatique"));
        assertFalse(estCleValide(" BUTINFORMATIQUE"));
        assertFalse(estCleValide("BUTINFORMATIQUE "));
    }

//////////////////////////////////////////////////////////////////////////

    // La fonction estChiffreOK vérifie que le chiffre passé en paramètre est valide (càd est un entier compris entre 0 et LARGEUR-1)
    // Par exemple :
    //  si messageCode=""01 24 23 14 24 40 32 ", la fonction retourne true
    //  si messageCode=""01 24 23 14 24 40 32", la fonction retourne false
    //  si messageCode=""01 24 23 14 24 40 3", la fonction retourne false
    //  si messageCode=""01 25 23 14 24 40 32 ", la fonction retourne false
    //  si messageCode=""01242314244032", la fonction retourne false

    boolean estChiffreOK(int chiffre){
        return (chiffre>=0 && chiffre<LARGEUR);
    }

    void testEstChiffreOK(){
        assertTrue(estChiffreOK(0));
        assertTrue(estChiffreOK(LARGEUR-1));
        assertFalse(estChiffreOK(-1));
        assertFalse(estChiffreOK(LARGEUR));

    }

    
    //////////////////////////////////////////////////////////////////////////

    // La fonction estMessageCodeValide vérifie que le message codé passé en paramètre est valide (càd constituée uniquement de paires d'entiers compris entre 0 et LARGEUR-1 et que chaque paire est séparée de la suivante par un espace, et un espace final)
    // Par exemple :
    //  si messageCode=""01 24 23 14 24 40 32 ", la fonction retourne true
    //  si messageCode=""01 24 23 14 24 40 32", la fonction retourne false
    //  si messageCode=""01 24 23 14 24 40 3", la fonction retourne false
    //  si messageCode=""01 25 23 14 24 40 32 ", la fonction retourne false
    //  si messageCode=""01242314244032", la fonction retourne false

    boolean estMessageCodeValide(String messageCode){
        //pour cette fonction on va deviser en partie le code chaque partie comprenant 3 caracteres , les 2 premiers
        //doivent etre des chiffre et le 3eme un espace
        boolean estValide=true;
        int cpt = 0;
        int taille=length(messageCode);
        int partie=0;
        if (taille % 3 != 0){//on test si le message est divisivle par 3 car si le message est correcte il est divisé 
        //en une ou plusieurs partie de 3 éléments donc la taille du texte est correcte si elle divisible par 3
            estValide=false;
        }
        while ((estValide)&&(cpt<taille)){
            if (cpt==(partie*3)+2){//si le compteur est égal a l'index 2 d'une partie alors on doit y trouver un espace
            //sinon il s'agit d'une erreur
                estValide=charAt(messageCode,cpt) ==' ';
                partie=partie+1;

            }else{//si le compteur est égal a l'index 0 ou 1 d'une partie alors on doit y trouver un numéro
            //compris en 0 et LARGEUR exclu sinon il s'agit d'une erreur
                estValide=estChiffreOK((int)(charAt(messageCode,cpt)-'0'));
            }
            cpt=cpt+1;
        }
        return estValide;
    
    }

    void testEstMessageCodeValide(){
        assertTrue(estMessageCodeValide("21 00 01 "));
        assertTrue(estMessageCodeValide("34 44 21 22 "));
        assertFalse(estMessageCodeValide("41 4"));
        assertFalse(estMessageCodeValide("45 41 "));

    }

    //////////////////////////////////////////////////////////////////////////

    // La fonction estMessageValide vérifie que le message passé en paramètre est valide (càd constitué uniquement de lettres de l'alphabet en majuscule)

    boolean estMessageValide (String message){
        boolean valide=true;
        int cpt=0;
        while((cpt<length(message))&&(valide)){//on appelle plusieurs fois la fonction estLettre majuscule pour verifie que tout le message est en majuscule 
            if (! estLettreMajuscule(charAt(message,cpt))){
                valide=false;
            }
            cpt=cpt+1;
            
        }
        return valide;
    }

    void testEstMessageValide(){
        assertTrue(estMessageValide("BUTINFORMATIQUE"));
        assertTrue(estMessageValide("GROUPEW"));
        assertTrue(estMessageValide(""));
        assertFalse(estMessageValide("BUT INFORMATIQUE"));
        assertFalse(estMessageValide("BUTinfOrMATIQUE"));
        assertFalse(estMessageValide("BUTINFORMATIQUE4"));

    }


        //////////////////////////////////////////////////////////////////////////
        // PROGRAMME PRINCIPAL
        //////////////////////////////////////////////////////////////////////////

        // Ecrire un programme principal qui :
        // 1. affiche un message d'introduction à l'utilisateur
        // 2. affiche un message à l'utilisateur demandant s'il veut utiliser une clé ?
        // 3. lit la réponse de l'utilisateur
        // 4. si l'utilisateur a répondu oui, demande et lit la clé souhaitée
        // 5. affiche le carré de Polybe (générique (càd sans clé) ou avec clé selon la réponse précédente de l'utilisateur)
        // 6. tant que la réponse n'est pas 0, affiche un menu et demande à l'utilisateur de saisir un entier (0 ou 1 ou 2 ou 3) pour :
        //              0. QUITTER
        //              1. CODER UN MESSAGE
        //              2. DECODER UN MESSAGE
        //              3. MODIFIER LE MODE AVEC/SANS CLE
        //        puis agit en conséquence.
        // NB : si et tant qu'une saisie de l'utilisateur n'est pas correcte, il faut la redemander (que ce soit pour la clé, le message à coder, le message à décoder ou le choix dans le menu)

	//Tout d'abord nous allons faire les 3 fonctions qui servent a la fonction principale
        String changerCarre(){
            String cle;//ici on demande a l'utilisateur d'entrer une cle jusqu'a ce qu'elle soit valide
            do{
                println("Entrez une cle pour initialiser un carre (En majuscule et sans W): ");
                cle=readString();
            }while (!estCleValide(cle));
            String carre = initialiserCarreAvecCle(cle);//ensuite on appelle la fonction initialiserCarreAvecCle pour creer un nouveau carre
            return carre;
        }

        void coderUnMessage(String carre){//ici on demande a l'utilisateur d'entrer un message jusqu'a ce qu'il soit valide
            String motACoder;
            do{
                println("Entrez un mot à coder (En Majuscule): ");
                motACoder=readString();
            }while (!estMessageValide(motACoder));
            String code = coderMessage(carre,motACoder);//ensuite on appelle la fonction coderMessage pour affciher le message code
            print(motACoder +" se code : " + code);
        }

        void decoderUnMessage(String carre){//ici on demande a un utilisateur d'entrer un code valide jusqu'a ce qu'il soit valide
            String code;
            do{
                println("Entrez un mot à decoder (Format : \"00 11 04 31 11 01 12 \") et sans depasser 4 et sans etre inferieur à 0 ");
                code = readString();
            }while (!estMessageCodeValide(code));
            println(code + " decoder veut dire " + decoderMessage(carre,code));//ensuite on appelle la commande decoderMessage afin d'afficher le message decode
        }


        void algorithm(){
            println("SAE1.01 - Le carré de Polybe");
            String code;//on creer toutes les fonctions obligatoire pour la fonction
            String cle;
            String motACoder;
            String carre;
            int fonction;
            carre=initialiserCarreSimple();//on initialise notre carre sans code 
            println("Bienvenue dans le crypteur décrypteur que souhaitez vous faire : ");
            do{//on entre dans une boucle qui tourne tant que on entre pas la valeur 4
                
                println("voici votre carre ");
                afficherCarre(carre);
                println("Que souhaitez vous faire : ");
                println("1 : change de carre de polybe  ");
                println("2 : code un message  ");
                println("3 : decode un message  ");
                println("4 : sortie  ");//on affiche le carre en demandant ce que l'utilisateur souhaite faire
                fonction=readInt();//on utilise un String afin de d'eviter des erreurs
                
                if (fonction==1){//ensuite on verifie valeur et utilisons la fonction adapte
                    carre=changerCarre();
                }
                else if(fonction==2){
                    coderUnMessage(carre);
                }
                else if(fonction==3){
                    decoderUnMessage(carre);
                }
            }while(fonction!=4);
            print("Merci d'avoir utiliser notre programme :)");
            
            
            


            
            

            

            println();

            
            
            
        }
    //////////////////////////////////////////////////////////////////////////  
}
