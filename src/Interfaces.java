//AUTEUR : Navarna
import javafx.application.Application;
import javafx.application.Application;
import javafx.stage.*;
import javafx.scene.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Paint;
import javafx.scene.canvas.*;
import javafx.scene.input.MouseEvent ; 
import javafx.geometry.*;
import javafx.scene.paint.Color ;
import javafx.scene.control.Button;
import javafx.event.*;
import javafx.scene.shape.*;
import javafx.scene.control.MenuBar ; 
import javafx.scene.control.Menu ;
import javafx.scene.control.MenuItem ;
import javafx.scene.control.ToolBar;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.text.TextAlignment;
import java.io.*;

public class Interfaces extends Application {
    BorderPane bp ;
    BorderPane gpEnHaut ;
    GridPane gpEnBas ; 
    ToolBar tool ; 
    MenuItem [] groupeItem ;
    Button arret ;
    Button valider ; 
    Menu [] groupeMenu ;
    MenuBar barre ;
    Canvas cv  ;
    GraphicsContext gc ; 
    Pane p ; 
    Label lab ; 
    TextField txf ; 

    boolean pointAj  = false ;
    boolean areteAj = false  ;  
    double nbAj [] = {0.0,0.0,0.0,0.0,0.0,0.0 }; 
    boolean pointSup = false ; 
    boolean pointModif = false ; 
    boolean translation = false  ; 
    boolean rotation = false ; 
    boolean style = false ; 
    boolean styleAre = false ; 
    boolean information = false ; 
    boolean informationTot = false ; 
    boolean poidsAuto = true ; 
    boolean modifDonnee = false ; 
    boolean finiSaisie = false ; 
    boolean algoD = false ; 
    int algo = -1 ; 
    Graphe g ; 
    Stage principal ; 

    public void start (Stage principal) {
	g = new Graphe() ; 
	bp = new BorderPane() ;
	gpEnHaut = new BorderPane() ; 
	gpEnBas = new GridPane() ; 
	p = new Pane() ; 
	Scene sc = new Scene(bp,800,600) ;
	tool = new ToolBar() ;
	lab = new Label() ;
	this.principal = principal ; 
	lab.setMinWidth(600);
	
	gpEnBas.add(lab,1,1);
	gpEnBas.add(tool,1,2);
	Menu () ; 

	ajouterLesActionsCanvas() ; 
	 
	bp.setBottom(gpEnBas);
	bp.setTop(gpEnHaut);  
	bp.setCenter(p) ; 

	principal.setTitle("Projet") ;
	principal.setScene(sc) ;
	principal.setResizable(false);
	principal.sizeToScene();
	principal.show() ;
    }

    public void  Menu () {
	barre = new MenuBar();
	groupeMenu = new Menu [7] ;
	groupeItem = new  MenuItem[21] ;
	groupeMenu [0] = new Menu("fichier") ;
	groupeMenu[1] = new Menu("modifier") ;
	groupeMenu [2] = new Menu("option") ;
	groupeMenu[3] = new Menu("Style"); 
	groupeMenu[4] = new Menu("Ajouter arete");
	groupeMenu[5] = new Menu ("poids") ;
	groupeMenu[6] = new Menu ("Algorithmes") ; 
	int i = 0 ;
	barre.getMenus().addAll(groupeMenu[0],groupeMenu[1] , groupeMenu[2], groupeMenu[6] ) ;
	String [] tabNom = {"sauver" , "quitter" ,"ajouter sommet ","deplacer" , "supprimer" ,"translation","rotation","information Total ","cercle","rectangle","droite","courbe","information","modifier les données","automatique" , "manuel","Dijkstra","Dijkstra  pas à pas" , "Dijkstra a l'envers","ouvrir","nouveau"};
	groupeItem[20] = new MenuItem(tabNom[20]) ; 
	groupeMenu[0].getItems().add(groupeItem[20]);
	groupeItem[19] = new MenuItem(tabNom[19]) ; 
	groupeMenu[0].getItems().add(groupeItem[19]);
	for(; i < 2 ; i ++){
	    groupeItem[i] = new MenuItem (tabNom[i]) ; 
	    groupeMenu[0].getItems().add(groupeItem[i]) ;
	}
	groupeItem[2] =  new MenuItem(tabNom[2]);
	groupeMenu[1].getItems().add(groupeItem[2]) ;
	groupeMenu[1].getItems().add(groupeMenu[4]) ;
	for (i=3 ; i  <  7 ; i++) {
	    groupeItem[i] = new MenuItem (tabNom[i]) ; 
	    groupeMenu[1].getItems().add(groupeItem[i]) ;
	}

	i = 8  ;
	for ( ; i< 10 ; i++) {
	    groupeItem[i] = new MenuItem(tabNom[i]) ;
	    if(i == 9 ){
		Rectangle rec = new Rectangle(10,10) ;
		groupeItem[i].setGraphic(rec) ; 
	    }
	    else {
		Circle cir = new Circle(5) ;
		groupeItem[i].setGraphic(cir) ; 
	    }
	    groupeMenu[3].getItems().add(groupeItem[i]) ;
	}
	for (; i < 12 ; i ++ ) {
	    groupeItem[i] = new MenuItem (tabNom[i]) ; 
	    groupeMenu[4].getItems().add(groupeItem[i]) ;
	}
	i = 14 ; 
	for(;i<16 ; i++) {
	    groupeItem[i] = new MenuItem (tabNom[i]) ; 
	    groupeMenu[5].getItems().add(groupeItem[i]) ;
	} 
	arret = new Button("Stop") ; 
	arret.setMinWidth(790);
	tool.getItems().add(arret) ;
	arret.setVisible(false) ; 

	groupeItem[7] = new MenuItem (tabNom[7]) ;
	groupeItem[12] = new MenuItem(tabNom[12]) ;
	groupeItem[13] = new MenuItem(tabNom[13]) ;
	groupeMenu[2].getItems().addAll(groupeItem[12],groupeItem[7],groupeItem[13],groupeMenu[3], groupeMenu[5]) ;
	
	groupeItem[16] = new MenuItem(tabNom[16]) ;
	groupeItem[17] = new MenuItem(tabNom[17]) ;
	groupeItem[18] = new MenuItem(tabNom[18]) ;
	groupeMenu[6].getItems().addAll(groupeItem[16],groupeItem[17],groupeItem[18]) ; 
	barre.setMinWidth(800);
        gpEnHaut.setTop(barre); 

	txf = new TextField () ; 
	txf.setVisible(false) ;
	txf.setMinWidth(690);
	valider = new Button("valider") ; 
	valider.setMinWidth(100);
	valider.setVisible(false) ; 
	gpEnHaut.setLeft(txf);
       	gpEnHaut.setRight(valider);
	
	ajouterLesActionsMenu() ; 
    }

    public void ajouterLesActionsMenu () {

	groupeItem[20].setOnAction(new EventHandler<ActionEvent>(){
		public void handle(ActionEvent event) {
		    nouveau () ;  
		    boutonArret(false) ;
		    reinitialiserBoolean() ;
		}
	    });

	groupeItem[19].setOnAction(new EventHandler<ActionEvent>(){
		public void handle(ActionEvent event) {
		    nouveau () ; 
		    FileChooser filechoser =new FileChooser();
		    filechoser.setTitle("charge file");
		    filechoser.setInitialFileName("masave.txt");
		    File selectedFile = filechoser.showOpenDialog(principal);
		    if (selectedFile != null) {
			g.charger(selectedFile);
			boutonArret(false) ;
			reinitialiserBoolean() ;
			afficher() ; 
			}
		}
	    });

	groupeItem[0].setOnAction(new EventHandler<ActionEvent>(){
		public void handle(ActionEvent event) {
		    FileChooser filechoser =new FileChooser();
		    filechoser.setTitle("Save file");
		    filechoser.setInitialFileName("masave.txt");
		    File selectedFile = filechoser.showSaveDialog(principal);
		    if (selectedFile != null) {
			g.sauver(selectedFile);  
			System.out.println("sauvegarde reussi rÃ©ussi");
		    }
		}
	    });
	groupeItem[1].setOnAction(new EventHandler<ActionEvent>(){
		public void handle(ActionEvent event) {
		    System.exit(0);
		}
	    });
	groupeItem[2].setOnAction(new EventHandler<ActionEvent>(){
		public void handle(ActionEvent event) {
		    if(pointAj) {
			reinitialiserBoolean () ; 
			boutonArret(pointAj) ; 
		    }
		    else {
			reinitialiserBoolean () ;
			pointAj = true ;
			boutonArret(pointAj) ; 
			String s ; 
			if(style) 
			    s = "rectangle";
			else 
			    s = "cercle" ; 
			lab.setText("Ajout d'un sommet : de type "+ s ) ; 
			lab.setTextAlignment(TextAlignment.CENTER);
		    }
		    
		}
	    });
	groupeItem[10].setOnAction(new EventHandler<ActionEvent>(){
		public void handle(ActionEvent event) {
		   
		    if (areteAj &&(!styleAre)) {
			reinitialiserBoolean () ; 
			boutonArret(areteAj) ; 
		    }
		    else {
			reinitialiserBoolean() ;
			areteAj = true ; 
			styleAre = false ; 
			boutonArret(areteAj) ; 
			lab.setText("Ajout d'une arête droite " ) ; 
		    }
		}
	    });
	groupeItem[3].setOnAction(new EventHandler<ActionEvent>(){
		public void handle(ActionEvent event) {
		    
		    if (pointModif ) {
			reinitialiserBoolean () ;
			boutonArret(pointModif) ;
		    }
		    else {
			reinitialiserBoolean() ;
		        pointModif = true ; 
			boutonArret(pointModif) ; 
			lab.setText("déplacement d'un sommet " ) ; 
		    }

		}
	    });
	groupeItem[4].setOnAction(new EventHandler<ActionEvent>(){
		public void handle(ActionEvent event) {
		    
		    if(pointSup) {
			reinitialiserBoolean () ; 
			boutonArret(pointSup) ;
		    }
		    else {
			reinitialiserBoolean () ;
			pointSup = true ;
			boutonArret(pointSup) ;
			g.supressionActiver(pointSup)  ; 
			lab.setText("suppression d'un sommet " ) ; 
		    }
		}
	    });
	groupeItem[5].setOnAction(new EventHandler<ActionEvent>(){
		public void handle(ActionEvent event) {
		  
		    if(translation) {
			reinitialiserBoolean () ; 
			boutonArret(translation) ; 
		    }
		    else {
			reinitialiserBoolean () ;
			translation = true ;
			boutonArret(translation) ; 
			lab.setText("translation du graphe " ) ; 
		    }
		}
	    });
	groupeItem[6].setOnAction(new EventHandler<ActionEvent>(){
		public void handle(ActionEvent event) {
		    if(rotation) {
			reinitialiserBoolean () ; 
			boutonArret(rotation) ; 
		    }
		    else {
			reinitialiserBoolean () ;
			rotation = true ;
			boutonArret(rotation) ; 
			lab.setText("rotation du graphe" ) ; 
		    
		    }
		}
	    });
	groupeItem[7].setOnAction(new EventHandler<ActionEvent>(){
		public void handle(ActionEvent event) {
		 
		    informationTot = !informationTot ;
		    g.informationTotal(informationTot) ;
		    information = false ; 
		    System.out.println(lab.getText()+"|"+"\n"+" " + g.information()) ;
		   
		    if(informationTot) 
			lab.setText(lab.getText() + " " + g.information());
		    else if( lab.getText().length() > 40 ) {
			lab.setText("") ; 
		    }
		    else {
		    }
			
		}
	    });
	groupeItem[8].setOnAction(new EventHandler<ActionEvent>(){
		public void handle(ActionEvent event) {
		    System.out.println("Je met un cercle  " ) ; 
		    style = false ; 
		}
	    });
	groupeItem[9].setOnAction(new EventHandler<ActionEvent>(){
		public void handle(ActionEvent event) {
		    System.out.println("Je met un rectangle  " ) ; 
		    style = true ;
		}
	    });

        groupeItem[11].setOnAction(new EventHandler<ActionEvent>(){
		public void handle(ActionEvent event) {
		   
		    if (areteAj && styleAre) {
			reinitialiserBoolean () ; 
		    }
		    else {
			reinitialiserBoolean() ;
			areteAj = true ; 
			styleAre = true ; 
			boutonArret(areteAj) ;
			lab.setText("Ajout d'une arête courbe " ) ; 
		    }
		}
	    });

	groupeItem[12].setOnAction(new EventHandler<ActionEvent>(){
		public void handle(ActionEvent event) {
        
		    information = !information ; 
		    informationTot = false ; 
		    g.informationTotal(false) ; 
		}
	    });
	groupeItem[13].setOnAction(new EventHandler<ActionEvent>(){
		public void handle(ActionEvent event) {
	        
		     if(modifDonnee) {
			reinitialiserBoolean () ;
			boutonArret(modifDonnee) ; 
		    }
		    else {
			reinitialiserBoolean () ;
			modifDonnee = true ;
			boutonArret(modifDonnee) ; 
			g.supressionActiver(modifDonnee)  ; 
			lab.setText("modification des données " ) ; 
		    }		   
		}
	    });

	groupeItem[14].setOnAction(new EventHandler<ActionEvent>(){
		public void handle(ActionEvent event) {
        
		    poidsAuto = true ;
		}
	    });

	groupeItem[15].setOnAction(new EventHandler<ActionEvent>(){
		public void handle(ActionEvent event) {
        
		    poidsAuto =false ; 
		}
	    });


	groupeItem[16].setOnAction(new EventHandler<ActionEvent>(){
		public void handle(ActionEvent event) {
		    
		     if (algoD  &&(algo == 0)) {
			reinitialiserBoolean () ;
			boutonArret(algoD) ;
		    }
		    else {
			reinitialiserBoolean() ;
		        algoD = true ; 
			algo = 0 ; 
			boutonArret(algoD) ; 
			lab.setText("Algorithmes de Dijkstra " ) ; 
		    }
		
		}
	    });

	groupeItem[17].setOnAction(new EventHandler<ActionEvent>(){
		public void handle(ActionEvent event) {
		     
		     if (algoD &&(algo == 1)) {
			reinitialiserBoolean () ;
			boutonArret(algoD) ;
		    }
		    else {
			reinitialiserBoolean() ;
		        algoD = true ; 
			algo = 1 ; 
			boutonArret(algoD) ; 
			lab.setText("Algorithmes de Dijkstra pas a pas" ) ; 
		    }
		
		}
	    });

	groupeItem[18].setOnAction(new EventHandler<ActionEvent>(){
		public void handle(ActionEvent event) {
        
		     if (algoD &&(algo == 2) ) {
			reinitialiserBoolean () ;
			boutonArret(algoD) ;
		    }
		    else {
			reinitialiserBoolean() ;
		        algoD = true ;
			algo = 2 ; 
			boutonArret(algoD) ; 
			lab.setText("Algorithmes de Dijkstra renverser" ) ; 
		    }
		
		}
	    });

	arret.setOnAction(new EventHandler<ActionEvent>(){
		public void handle(ActionEvent event) {

		    reinitialiserBoolean() ;
		    boutonArret(false) ; 
		   
		}
	    });
	valider.setOnAction(new EventHandler<ActionEvent>(){
		public void handle(ActionEvent event) {	
		    String s = txf.getText() ; 
		    if(nbAj[0] == 1.0){
			int position = (int) nbAj[1] ; 
			g.modificationNom(position , s) ;
			g.informationSommet(position,informationTot) ;
			txf.setText("");
			nbAj[0] = 0.0 ;
			nbAj[1] = 0.0 ;
			ecriture(false) ; 
		    }
		    else if (nbAj[0] == 2.0 ) {
			int position = (int) nbAj[1] ; 
			try {
			    int a=Integer.parseInt(s);
			    g.modificationPoid(position ,a);
			    g.textNoir(position);
			    txf.setText("");
			    nbAj[0] = 0.0 ;
			    nbAj[1] = 0.0 ;
			    ecriture(false) ; 
			    g.initialiserCouleur(true) ; 
			}
			catch(Exception e) {
			    txf.setText("") ; 
			    txf.setPromptText("Ceci n'est pas un nombre ") ;
			} 
		    }
		    
		    
		}
	    });
    }

    public void ajouterLesActionsCanvas () {
	p.setOnMouseReleased (new EventHandler<MouseEvent>() {
		public void handle (MouseEvent event ) {
		    if(pointAj){
        
			creationPoint(event.getX() , event.getY() ) ; 
		    }  
		    if(pointSup) {
	        
			supressionPoint(event.getX() , event.getY() ) ; 
			supressionArete(event.getX() , event.getY() ) ; 
			
		    }
		    if(areteAj) {
	        
			if(nbAj[0] == 0.0) { 
			    nbAj[0] = event.getX() ;
			    nbAj[1] = event.getY() ; 
			}
			else if (nbAj[2] ==0.0){
			    nbAj[2] = event.getX() ; 
			    nbAj[3] = event.getY() ;
			    if(!styleAre) 
				creationAreteL () ;

			    }
			else {
			    if(styleAre) {
				nbAj[4] = event.getX() ;
				nbAj[5] = event.getY() ;
				creationAreteC() ; 
			    }
			}
		    }
		
		    
		    if(pointModif) {
	        
			if(nbAj[0] == 0.0) { 
			    nbAj[0] = event.getX() ;
			    nbAj[1] = event.getY() ; 
			}
			else {
			    nbAj[2] = event.getX() ; 
			    nbAj[3] = event.getY() ;  
			    modificationPoint();
			}
		    }
		    if(translation ) {
	        
			if(nbAj[0] == 0.0) { 
			    nbAj[0] = event.getX() ;
			    nbAj[1] = event.getY() ; 
			}
			else {
			    nbAj[2] = event.getX() ; 
			    nbAj[3] = event.getY() ;  
			    translationGraphe() ; 
			}
		        
		    }
		    if(rotation){
	        
			rotationGraphe(event.getX() , event.getY() ) ; 
		    }
		    if(information) {
	        
			informationUnique(event.getX() ,  event.getY(), false ) ; 
		    }
		    if(modifDonnee) {
			
			int okS = ModificationDesDonneeSommet(event.getX() , event.getY() ) ;
        
			if(okS== -1 ) {
			    
			    int okA = ModificationDesDonneeArete () ;
        
			    if(okA != -1 ) {
				nbAj[0] = 2 ; 
				nbAj[1] =okA ; 
				ecriture(true) ; 
				g.modifieTouche(okA,false) ; 
			    }
			} 
			else {
			    nbAj[0] = 1 ;
			    nbAj[1] = okS ; 
			    ecriture(true) ;
			    }
		    }

		    if(algoD) {
	        
			if(nbAj[0] == 0.0) { 
			    nbAj[0] = event.getX() ;
			    nbAj[1] = event.getY() ; 
			}
			else if(nbAj[2] == 0.0) {
			    nbAj[2] = event.getX() ; 
			    nbAj[3] = event.getY() ; 
			    if(algo == 0 ) {
				algoDijstra(true,true) ; 
				nbAj[0] =0.0 ; 
				nbAj[1] = 0.0 ; 
				nbAj[2]= 0.0 ;
				nbAj[3]= 0.0 ;
			    }
			    else if (algo == 1){
				algoDijstra(false,true) ; 
			    }	
			    else if(algo == 2) {
				algoDijstra(true,false) ;
			    }
			}
			else {
			  nbAj[4] = event.getX() ; 
			  nbAj[5] = event.getY() ;
			  algoDijstraM() ; 
			}

		    }
		}
	    });

	p.setOnMousePressed( new EventHandler<MouseEvent>() {
		public void handle (MouseEvent event ) {
		    if(information) {
			informationUnique(event.getX() ,  event.getY(), true) ; 
		    }
		    
		}

	    });
    }


	    

    public void reinitialiserBoolean () {
	pointAj  = false ;
	areteAj = false  ;  
	nbAj[0] =0.0 ; 
	nbAj[1] = 0.0 ; 
	nbAj[2]= 0.0 ;
	nbAj[3]= 0.0 ;
	nbAj[4] = 0.0 ;
	nbAj[5] = 0.0 ; 
	if((pointSup) ||(modifDonnee))
	    g.supressionActiver(false) ; 
	pointSup = false ; 
	pointModif = false ; 
	translation = false  ; 
	rotation = false ;  
	styleAre = false ; 
	modifDonnee = false ; 
	if(informationTot) {
	    String s = g.information() ;
	    lab.setText(s) ;
	}
	else 
	    lab.setText("") ; 
	ecriture(false) ; 
	if(algoD){
	    g.initialiserCouleur(true) ;
	    g.setStop(true) ; 
	}

	algoD = false ; 
	algo = -1 ; 
	
    }
    public void boutonArret(boolean b) {
	if(b)  
	    arret.setVisible(true) ;
	else 
	    arret.setVisible(false) ;
    }


    public void creationPoint( double x ,double y ) {
	int i = g.rechercheSommetXY(x,y) ; 
	if( i == -1) {
	    g.ajouterSommet(x,y,this.style);
	    if(style){ 
		Rec nouveau = (Rec) g.rechercheSommet(g.tailleListeS()-1) ; 
		p.getChildren().add(nouveau.sh);
		p.getChildren().add(nouveau.text) ;
		nouveau.text.toFront() ; 
		
	    }
	    else {
		Cir nouveau = (Cir) g.rechercheSommet(g.tailleListeS()-1) ;
		if(nouveau == null) 
		    System.out.println("erreur") ; 
		else {
		p.getChildren().add(nouveau.sh);
		p.getChildren().add(nouveau.text) ; 
		nouveau.text.toFront() ; 
		}
	    } 
	    if(informationTot) { 
		g.informationSommet(g.tailleListeS() -1,informationTot ) ; 
	    }
	}
	else {
	    System.out.println("Il existe déjà un sommet à cette position" ) ;
	} 
    }
    
    public void supressionPoint (double x , double y ) {
	int i = g.rechercheSommetXY(x,y) ; 
	if(i != -1) {
	    Sommet s = g.getPositionS(i) ; 
	    g.supprimerSommet(i);
	    p.getChildren().remove(s.text) ; 
	    if(s.getType()) {
		Rectangle r = ((Rec) s).sh ;  
		p.getChildren().remove(r);
	    }
	    else {
		Circle c = ((Cir) s).sh ; 
		p.getChildren().remove(c) ; 
	    }
	    Arete a [] = g.rechercheArete(s) ; 
	    if(a!= null) {
		for (int k = 0 ; k < a.length ; k++) {
		    if(a[k]!= null) {
			g.supprimerArete(a[k]) ; 
		        if(a[k].getType()) {
			    Courbe ac = (Courbe) a[k] ; 
			    p.getChildren().remove(ac.l) ;
			    p.getChildren().remove(ac.text) ; 
			}
			else {
			    Ligne al = (Ligne) a[k] ;
			    p.getChildren().remove(al.l) ; 
			    p.getChildren().remove(al.text) ; 
			}
		    }
		} 
	    }
	}
	
    }

    public void creationAreteL ( ) {
	int i = g.rechercheSommetXY(nbAj[0] , nbAj[1] ) ; 
	int j = g.rechercheSommetXY(nbAj[2] , nbAj[3]) ; 
	if((i != -1) &&(j!=-1) &&(i!=j)){
	    Sommet s1 = g.getPositionS (i) ; 
	    Sommet s2 = g.getPositionS (j ) ;
	    if(!g.rechercheArete2(s1,s2) ) {
		g.ajouterAreteL(s1,s2, poidsAuto) ; 
		Ligne nouveau = (Ligne) g.rechercheArete(s1,s2); 
		p.getChildren().add(nouveau.l) ; 
		p.getChildren().add(nouveau.text) ; 
		nouveau.l.toBack() ;
	    }
	    else {
		System.out.println("Cette arete existe deja ") ; 
	    }	    
	}
	 this.nbAj [0] = 0.0; 
	 this.nbAj[1] = 0.0 ;
	 this.nbAj[2] = 0.0 ;
	 this.nbAj[3] = 0.0 ; 
	 this.nbAj[4] = 0.0 ;
	 this.nbAj[5] = 0.0 ; 
    }

    public void creationAreteC () {
	int i = g.rechercheSommetXY(nbAj[0] , nbAj[1] ) ; 
	int j = g.rechercheSommetXY(nbAj[4] , nbAj[5]) ; 
	if((i != -1) &&(j!=-1) &&(i!=j)){
	    Sommet s1 = g.getPositionS (i) ; 
	    Sommet s2 = g.getPositionS (j ) ;
	    if(!g.rechercheArete2(s1,s2) ) {
		g.ajouterAreteC(s1,s2,nbAj[2] , nbAj[3],poidsAuto) ; 
		Courbe nouveau = (Courbe) g.rechercheArete(s1,s2); 
		p.getChildren().add(nouveau.l) ; 
		p.getChildren().add(nouveau.text) ; 
		nouveau.l.toBack() ; 
	    }
	    else {
		System.out.println("Cette arete existe deja ") ; 
	    }	    
	}
	 this.nbAj [0] = 0.0; 
	 this.nbAj[1] = 0.0 ;
	 this.nbAj[2] = 0.0 ;
	 this.nbAj[3] = 0.0 ; 
	 this.nbAj[4] = 0.0 ;
	 this.nbAj[5] = 0.0 ; 
    }
    
    public void supressionArete(double x , double y ) {
	Arete a = g.rechercheAreteToucher() ;
	if(a != null ) {
	    g.supprimerArete(a) ;
	    if(a.getType()) {
		Courbe ac = (Courbe) a ; 
		p.getChildren().remove(ac.l) ; 
		p.getChildren().remove(ac.text) ; 
	    }
	    else {
		Ligne al = (Ligne) a ;
		p.getChildren().remove(al.l) ; 
		p.getChildren().remove(al.text) ; 
	    }
	}
    }

    public void modificationPoint ( ) {
	int i = g.rechercheSommetXY(nbAj[0],nbAj[1]) ;
	int j = g.rechercheSommetXY(nbAj[2], nbAj[3]);
	if( (i!= -1) &&(j == -1)){
	    Sommet s = g.getPositionS(i) ; 
	    double xpred  = s.getX() ; 
	    double ypred = s.getY() ; 
	    g.deplacerSommet(i , nbAj[2] , nbAj[3]) ; 
	    g.deplacerArete (s , xpred , ypred ) ; 
	    
	    
	}
	else {
	    System.out.println("donnée non correcte" ) ; 
	}
	this.nbAj[0] = 0.0 ;
	this.nbAj[1] = 0.0 ; 
    }

    public void translationGraphe ( ) {
	int i = g.rechercheSommetXY(nbAj[0],nbAj[1]) ;
	int j = g.rechercheSommetXY(nbAj[2], nbAj[3]);
	if( (i!= -1) &&(j == -1)){
	    Sommet s0 = g.getPositionS(i) ; 
	    double xpred  = s0.getX() ; 
	    double ypred = s0.getY() ; 
	    g.deplacerSommet(i , nbAj[2] , nbAj[3]) ; 
	    g.deplacerArete (s0 , xpred , ypred ) ; 
	    double deplacementX = nbAj[2] - xpred ;
	    double deplacementY = nbAj[3] - ypred ; 
	    for (int k = 0 ; k < g.tailleListeS() ; k++) {
		if(k != i ) {
		    Sommet s = g.getPositionS(k ) ; 
		    xpred = s.getX() ; 
		    ypred = s.getY() ;
		    
		    g.deplacerSommet(k, xpred+deplacementX , ypred+deplacementY ) ;
		    g.deplacerArete (s , xpred , ypred ) ;
		}
		}
	}
	else {
	    System.out.println("donnée non correcte" ) ; 
	}
	this.nbAj[0] = 0.0 ;
	this.nbAj[1] = 0.0 ; 
    }

    public double [] positionTranslation (double xpred , double ypred , double x , double y ) {
	double [] reponse = new double [2];
	reponse[0] = x*2 - xpred ; 
	reponse[1] = y*2 -ypred ; 
	return reponse ; 
    }

    public void rotationGraphe (double x, double y ) {
	int i = g.rechercheSommetXY(x,y) ;
	if (i != - 1) {
	    Sommet s0 = g.getPositionS(i) ; 
	    double x0  = s0.getX() ; 
	    double y0 = s0.getY() ; 
	    for (int k = 0 ; k < g.tailleListeS() ; k++) {
		if(i != k ) {
		    Sommet s = g.getPositionS(k ) ; 
		    double xpred = s.getX() ; 
		    double ypred = s.getY() ;
		    double [] xy = positionRotation (x0,y0,xpred , ypred ) ; 
		    g.deplacerSommet(k, xy[0] , xy[1]) ;
		    g.deplacerArete (s , xpred , ypred ) ;
		}
	    }
	}
    }

    public double [] positionRotation (double x0 , double y0 ,double xpred , double ypred ) {
	if((xpred <x0)&&(ypred> y0)){
	    return positionTranslation(xpred , ypred , x0,ypred) ; 
	} 
	else if ((xpred >x0)&&(ypred >y0)) {
	    return positionTranslation (xpred, ypred , xpred , y0) ; 
	}
	 
	else if ((xpred >x0)&&(ypred < y0)) {
	    return positionTranslation (xpred, ypred ,x0, ypred ) ;
	    
	}
	else if ((xpred < x0)&&(ypred <y0)) {
	    return positionTranslation(xpred , ypred ,xpred , y0) ;
	}
	else 
	    return null ;
	
    }

    public void informationUnique (double x , double y , boolean b ) {
	int i = g.rechercheSommetXY(x,y) ;
	if(i != -1) {
	    g.informationSommet(i,b) ; 
	}
    }

    public int ModificationDesDonneeSommet(double x, double y){
	return g.rechercheSommetXY(x,y) ;
	
    } 
    public int ModificationDesDonneeArete () {
	return g.rechercheAreteXY() ;
    } 
    

    public void ecriture (boolean b ) {
	this.txf.setVisible(b) ; 
	this.valider.setVisible(b)  ; 
    }

    public void algoDijstra( boolean b , boolean s) {
        int i = g.rechercheSommetXY(nbAj[0],nbAj[1]) ;
	int j = g.rechercheSommetXY(nbAj[2], nbAj[3]);
	if( (i!= -1) &&(j != -1)&&(i!=j)){
	    g.dijkstra (i,j,b,s) ; 
	}
	else {
	    System.out.println("Donnée non correcte") ; 
	    g.initialiserCouleur(false) ; 
	}
	if(b) {
	this.nbAj [0] = 0.0; 
	this.nbAj[1] = 0.0 ;
	this.nbAj[2] = 0.0 ;
	this.nbAj[3] = 0.0 ; 
	}
    }

    public void algoDijstraM() {
	int i = g.rechercheSommetXY(nbAj[4],nbAj[5]) ;
	if(i != -1) {
	    g.dijstraM(i);
	}
	else {
	    System.out.println("Donnée non correcte dedans ") ; 
	}
	nbAj[4] = 0.0 ; 
	nbAj[5] = 0.0 ; 
    }

    public void nouveau() {
	p.getChildren().clear(); 
	g = new Graphe () ; 
	g.reset() ; 
    }

    public void afficher() {
	for (int i = 0 ; i < g.listeSom.size() ; i++) {
	    if(!g.listeSom.get(i).getType()) {
		Cir c =(Cir) g.listeSom.get(i) ; 
		p.getChildren().add(c.sh) ;
		p.getChildren().add(c.text); 
		c.text.toFront() ; 
	    }
	    else {
		Rec r = (Rec)g.listeSom.get(i) ; 
		p.getChildren().add(r.sh) ;
		p.getChildren().add(r.text); 
		r.text.toFront() ;  
	    }
	    if(informationTot) { 
		g.informationSommet(i,informationTot ) ; 
	    }
		    
	}

	for (int i = 0 ; i < g.listeAre.size() ; i++) {
	    if(g.listeAre.get(i).getType()){
		Courbe nouveau = (Courbe) g.listeAre.get(i); 
		p.getChildren().add(nouveau.l) ; 
		p.getChildren().add(nouveau.text) ; 
		nouveau.l.toBack() ; 
	    }
	    else {
	        Ligne nouveau = (Ligne) g.listeAre.get(i); 
		p.getChildren().add(nouveau.l) ; 
		p.getChildren().add(nouveau.text) ; 
		nouveau.l.toBack() ; 
	    }
	}
    }


    public static void main (String [] args) {
	launch() ; 
    }


}
