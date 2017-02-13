//AUTEUR : Navarna
import java.util.*;
import java.lang.Math;
import java.io.*;

public class Graphe {
    public List<Sommet> listeSom ; 
    public List<Arete> listeAre ; 
    private int taille;  
    private int compteurS ;  
    private int compteurA ; 
    protected boolean stop = false  ; 

    public Graphe () {
	this.listeSom  = new LinkedList<Sommet> () ; 
	this.listeAre = new LinkedList<Arete>() ; 
	this.taille = 10 ; 
	this.compteurS = 0 ; 
	this.compteurA = 0 ;
    }

    public void ajouterSommet (double x , double y , boolean type ){
	Sommet nouveau ; 
	if(type) 
	    nouveau = new Rec (x,y,this.taille) ;
	else 
	    nouveau = new Cir (x,y,this.taille);
	listeSom.add(nouveau ) ; 
	nouveau.positioner() ;
	nouveau.informationAjour() ; 
	this.compteurS ++ ; 
    } 


    public void ajouterAreteL (Sommet un , Sommet deux , boolean pA) {
	Arete nouvelle ; 
	nouvelle = new Ligne (un.getId() , deux.getId() , pA) ; 
	listeAre.add(nouvelle); 
	this.compteurA ++ ; 
	nouvelle.positioner (un.getX() , un.getY() , deux.getX () , deux.getY() );
	modifierAreteSommet(un.getId() ,  deux.getId() , true ) ; 
    }

    public void ajouterAreteC (Sommet un , Sommet deux , double x , double y , boolean pA) {
	Courbe nouvelle ; 
	nouvelle = new Courbe (un.getId() , deux.getId(), pA ) ; 
	listeAre.add(nouvelle); 
	this.compteurA ++ ; 
	nouvelle.positioner (un.getX() , un.getY() , deux.getX () , deux.getY() );
	nouvelle.positionerCourbe (x,y ) ; 
	modifierAreteSommet(un.getId() ,  deux.getId() , true ) ; 
    }

    public void modifierAreteSommet (int unId , int deuxId , boolean b ) {
	Iterator it =  listeSom.iterator() ;
	int pointeur = 0 ; 
	while(it.hasNext() ) {
	    Sommet s =(Sommet) it.next() ; 
	    if((s.getId() == unId )||(s.getId() == deuxId))  {
		this.listeSom.get(pointeur).modifieArete(b) ; 
		this.listeSom.get(pointeur).informationAjour() ; 
	    }
	    pointeur ++ ; 
	}
    }

    public void supprimerSommet (int position ) {
	if(position >= 0 ) 
	    this.listeSom.remove(position) ; 
	
    }

    public Arete[] rechercheArete (Sommet i) {
	Iterator it =  listeAre.iterator() ;
	Arete reponse [] = new Arete [this.compteurA ] ;
	int pointeur = 0 ; 
	while(it.hasNext() ) {
	    Arete a =(Arete) it.next() ; 
	    if(a.estUnSommet(i)) {
		reponse[pointeur] = a ; 
		pointeur ++ ; 
	    }				 
	}
	if(pointeur ==  0) 
	    return null ; 
	return reponse  ;
    }

    public Arete rechercheArete (Sommet un ,Sommet deux ) {
	Iterator it =  listeAre.iterator() ;
	while(it.hasNext() ) {
	    Arete a =(Arete) it.next() ; 
	    if(a.estUnSommet(un)&& a.estUnSommet(deux)) 
		return a ; 
	}
	return null;
    }

    public int rechercheAreteXY() {
	Iterator it =  listeAre.iterator() ;
	int position = 0 ;  
	while(it.hasNext() ) {
	    Arete a =(Arete) it.next() ; 
	    if(a.getToucher()) 
		return position ;
	    position ++ ; 
	}
	return -1 ; 
    }

    public Arete rechercheAreteToucher () {
	Iterator it =  listeAre.iterator() ;
	while(it.hasNext() ) {
	    Arete a =(Arete) it.next() ; 
	    if(a.getToucher()) 
		return a ; 
	}
	return null ; 
    }

    public boolean rechercheArete2(Sommet un , Sommet deux ) {
	if(rechercheArete(un , deux) == null) 
	    return false ; 
	return true ; 
    }

     public int rechercheArete (int unId ,int deuxId ) {
	 for(int i = 0 ; i < this.listeAre.size() ; i++) {
	     if(this.listeAre.get(i).estUnSommet(unId)&& this.listeAre.get(i).estUnSommet(deuxId)) 
		 return i ;
	}
	return -1;
     }

    public void supprimerArete ( Arete a ) {
	modifierAreteSommet(a.getSommet(0) , a.getSommet(1), false ) ; 
	this.listeAre.remove(a) ; 
	this.compteurA -- ; 
    }

    public Sommet rechercheSommet (int i) {
	Iterator it =  listeSom.iterator() ;
	while(it.hasNext() ) {
	    Sommet s =(Sommet) it.next() ; 
	    if(s.getId() == i ) 
		return s ;
	  
	}
	return null ; 
    }

    public int rechercheSommetId (int id) {
        for(int i = 0 ; i  < this.listeSom.size() ; i ++) {
	    if(this.listeSom.get(i).getId() == id) 
		return i ; 
	}
	return -1 ; 
    }

    public boolean rechercheSommet2 (int i ) {
	Sommet rep = rechercheSommet(i) ;
	if(rep == null) 
	    return false ;
	return true ;
    }


    public int rechercheSommetXY (double x , double y ) {
	Iterator it =  listeSom.iterator() ;
	int position = -1 ; 
	while(it.hasNext() ) {
	    Sommet s =(Sommet) it.next() ; 
	    position ++ ; 
	    if(((s.getX() >= x-this.taille/2 )&&(s.getX() <= x+ this.taille/2 ))&&((s.getY() >= y-this.taille/2 )&&(s.getY() <= y+this.taille/2))) 
		return position;
	  
	}
	return -1 ; 
    }

    public void deplacerSommet ( int position , double x , double y ){
	this.listeSom.get(position).setX(x) ;
	this.listeSom.get(position).setY(y) ; 
	this.listeSom.get(position).positioner() ; 
    } 
    
    public void deplacerArete (Sommet s, double x0 , double y0 ) {
	Iterator it =  listeAre.iterator() ;
	int position = 0 ; 
	while(it.hasNext() ) {
	    Arete a =(Arete) it.next() ; 
	    if(a.estUnSommet(s)) {
		this.listeAre.get(position).deplacer(x0,y0,s.getX() , s.getY() ) ;
	    }
	    position ++ ; 
	}
    }

    public void supressionActiver(boolean b ) {
	Iterator it =  listeAre.iterator() ; 
	int position = 0 ; 
	while(it.hasNext() ) {
	    it.next() ; 
	    this.listeAre.get(position).setDemandeSupression(b) ;	    
	    position ++ ; 
	}
    }
    

    public void affichage () {
	int compteurSommet = 0 , compteurArete = 0 ; 
	Iterator it = listeSom.iterator () ; 
	Iterator it2 = listeAre.iterator () ;
	while (it.hasNext()) {
	    System.out.println(it.next()+"\n" ) ;
	    compteurSommet ++ ; 
	}
	while(it2.hasNext() ) {
	    System.out.println(it2.next() +"\n") ;
	    compteurArete ++ ; 
	}
	System.out.println("Nombre de Sommet : " + compteurSommet + "\nNombre d'arète : " + compteurArete ) ; 

    }

    public String information ( ) {
	String info = "Nombre de sommet : " + this.listeSom.size()  + "  et Nombre d'arete : "  +  this.listeAre.size();
	return info ; 
    }

    public void informationTotal (boolean b ) {
	Iterator it = listeSom.iterator () ; 
	int position = 0  ; 
	while(it.hasNext()) {
	    it.next () ; 
	    this.listeSom.get(position).information(b) ;
	    position ++ ; 
	} 
    }

    public void informationSommet (int position, boolean b) {
	    this.listeSom.get(position).information(b) ; 	
    }

    public void modificationNom (int position , String nouveau) {
	this.listeSom.get(position).setNom(nouveau) ;
    }
    public void modificationPoid (int position , int nouveau) {
	this.listeAre.get(position).setPoid(nouveau) ;
	this.listeAre.get(position).setPoidAuto(false) ; 
	this.listeAre.get(position).affichagePoids(); 
    }
    public void modifieTouche(int position,boolean b){
	this.listeAre.get(position).setToucher(b) ; 
       
    } 
    public void textNoir(int position) {
	this.listeAre.get(position).textNoir();
    }
    public void setCompteurS (int c ) {
	this.compteurS = c ; 
    }

    public int getCompteurS() {
	return this.compteurS ; 

    }

    public void setCompteurA ( int c ) {
	this.compteurA = c ; 
    }

    public int getCompteurA () {
	return this.compteurA ; 
    }
    public Sommet getPositionS( int position ) {
	return this.listeSom.get(position) ; 
    }

    public int tailleListeS () {
	return this.listeSom.size() ; 
    }

    public void setStop (boolean s) {
	this.stop = s; 
    }

    public void initialisationDijkstra (int positionDeb , List<Sommet> reste) {
	for (int i = 0 ;i <this.listeSom.size() ; i++) {
	    this.listeSom.get(i).setDistanceAlgo(-1) ; 
	    this.listeSom.get(i).setSommetPrec(-1); 
	    if(i == positionDeb) {
		this.listeSom.get(i).setDistanceAlgo(0) ;
	        
		}
	    Sommet s  = this.listeSom.get(i);
	    reste.add(s) ; 
	}
	
    }

    public int trouver_min(List<Sommet> reste) {
	int poidsMin = -1 ;
	int retour = -1 ; 
	for (int j = 0 ; j < reste.size() ; j++) {
	    int p = reste.get(j).getDistanceAlgo() ;
	    if(p!= -1){
		if((p < poidsMin)||(poidsMin == -1 ) ) {
		    retour = j ; 
		    poidsMin =p ;
		}
	    }
	}
	return retour ; 
    }

    public void majDistance (int positionDeb ,int positionFin ) {
	Arete a = rechercheArete (this.listeSom.get(positionFin) , this.listeSom.get(positionDeb));
	if(a == null) {
	    System.out.println("probleme de l algo fonction MajDistance , pas d'arete") ; 
	}
	else if(this.listeSom.get(positionFin).getDistanceAlgo() == -1) {
	    
	    this.listeSom.get(positionFin).setDistanceAlgo(this.listeSom.get(positionDeb).getDistanceAlgo() + a.getPoid());
	    this.listeSom.get(positionFin).setSommetPrec(this.listeSom.get(positionDeb).getId());
	}
	else if (this.listeSom.get(positionFin).getDistanceAlgo() > (this.listeSom.get(positionFin).getDistanceAlgo() + a.getPoid())){
	    
	    this.listeSom.get(positionFin).setDistanceAlgo(this.listeSom.get(positionDeb).getDistanceAlgo() + a.getPoid());
	    this.listeSom.get(positionFin).setSommetPrec(this.listeSom.get(positionDeb).getId());
	}
	else {
	}
    }
    

    public String dijkstra (int positionDeb , int positionFin , boolean visible , boolean sens  ) {
        
	initialiserFichier("tmp.txt") ; 
	temps t ; 
	if (sens) 
	    t = new temps (0) ;
	else 
	    t = new temps(2) ;
	boolean fini = false ; 
	try{
	    File f = new File("tmp.txt") ; 
	    FileWriter fw = new FileWriter (f) ; 
	    String copie = "Algo\n";
	    fw.write(copie) ; 
	    copie= "R\n";
	    fw.write(copie) ;
	    List<Sommet> reste = new LinkedList<Sommet> ();
	    initialisationDijkstra(positionDeb, reste) ;	
	    while(!reste.isEmpty()&&!fini) {	   
		int s0 = trouver_min(reste) ; 
		if(s0 == -1 ) {
  		    fini = true ; 
		}
		else {
		    Sommet s = reste.get(s0) ;
		    reste.remove(s0) ;
		    int i = rechercheSommetId(s.getId() ) ; 
		    copie = "S " + i+" 2\n" ; 
		    fw.write(copie) ; 
		    Arete a [] = rechercheArete(s) ; 
		    if (a != null) {
			for (int k =0; k  <a.length ; k ++ ) {
			    if(a[k] != null) {
				int voisin = a[k].autreSommet(s.getId() ) ;
				int j = rechercheSommetId(voisin) ;  
				boolean dejaVu = false ; 
				if((this.listeSom.get(j).getSommetPrec() != -1)||(j == positionDeb)) {
				    dejaVu = true ; 
				}
				int acolor = rechercheArete(i,j) ; 
				majDistance(i,j) ; 
			        
				copie = "A " + acolor + " 2\n" ; 
				if(!dejaVu) {
				    if(!sens){
					copie +="S " + j +" 0\nA " + acolor + " 0\n";

				    }   
				    copie +="S " + j +" 4\n";
				    fw.write(copie) ;
				}
				 
				
			   
			    }
			}
		    }
		    copie= "S " + i + " 1\n";
		    fw.write(copie) ;  
		    
		}
	    }
	    fini = false ; 
	    List<Integer> chemin = new LinkedList<Integer> () ;
	    int pred= this.listeSom.get(positionFin).getId() ;
	    int debut = this.listeSom.get(positionDeb).getId();
	    String s0 = "Chemin de " +debut + " à " + pred +" : "; 
	    String s  = "" ; 
	    int rep = -1 ;
	    copie= "R\n";
	    fw.write(copie) ;  
	    while((pred != debut)&&!fini) {
		rep = rechercheSommetId(pred);
	    
		s ="->" + rep +s ;
		if((rep == -1 )&&(pred != debut)) {
		    fini = true ;
		}
		else {
		    int predP = pred ; 
		    pred = this.listeSom.get(rep).getSommetPrec();
		    int a = rechercheArete(pred,predP);
		    copie = "" ; 
		    if(a!= -1) {
			copie= "A " + a + " 3\n";
			      
		    }
		    copie += "S " + rep + " 3\n";
		    fw.write(copie) ;  
		}
	    }
    
	    if(fini) {
		copie= "R\n";
		fw.write(copie) ;  
		initialiserCouleur(false) ; 
		s = " aucun" ; 
		s0+=s;
	    
	    }
	    else {
		copie = ""; 
		s = pred + s ; 
		int a = rechercheArete(pred,this.listeSom.get(rep).getId());
		if(a!= -1) {
		    copie= "A " + a + " 3\n";
		}      
			
		copie += "S " + pred + " 3\n";
		fw.write(copie) ; 
		s0+=s ;
	    }
	
	    fw.close() ; 
	    if(visible) 
		t.go() ;
 
	    return s0 ; 
	}
	catch(Exception e) {
	    System.out.println("erreur algo : " +  e.getMessage())  ; 
	    return "erreur"; 
	}
    }
	    
	

    public void initialiserCouleur(boolean b) {
	for (int i = 0 ; i < this.listeSom.size() ; i ++) {
	    if(!b) 
		this.listeSom.get(i).colorer(0);
	    else {
		if(this.listeSom.get(i).getType()) {
		    this.listeSom.get(i).colorer(4);
		}
		else {
		    this.listeSom.get(i).colorer(1);
		}
	    }
	}

	for (int i = 0 ; i < this.listeAre.size() ; i ++) {
	    this.listeAre.get(i).colorer(0);
	}
	
    }

    public void initialiserFichier(String nom) {
	try {
	    FileOutputStream fos = new FileOutputStream( nom );
	    fos.close( );
	} catch (Exception e) {}
    }
    
    public void dijstraM(int i ) {
	temps t = new temps(1) ;
	t.setIdCommence(i) ; 
	t.go() ; 
    }


    public void sauver (File f ) {
	try {
	    System.out.println("dedans") ; 
	    FileWriter fw = new FileWriter (f) ;
	    String copie ="" ;
	    for (int i = 0 ; i < this.listeSom.size() ; i++) {
		copie += this.listeSom.get(i).sauver() +"\n" ; 
	    }
	    fw.write(copie) ; 
	    copie = ""; 
	    for (int i = 0 ; i < this.listeAre.size() ; i++) {
		int id1 = this.listeAre.get(i).getSommet(0) ; 
		int id2 = this.listeAre.get(i).getSommet(1) ; 
		int posi1 = rechercheSommetId(id1) ;
		int posi2 = rechercheSommetId(id2) ;
		copie += this.listeAre.get(i).sauver()+" " +posi1 + " " + posi2 +"\n" ; 
	    }
	    fw.write(copie) ; 
	    fw.close()  ; 
	}
	catch(Exception e) {}
    }
    public void reset () {
	Rec test = new Rec (0.0,0.0,0) ; 
	test.reset() ; 
    }
    public void charger (File f) {
	try {
	   FileInputStream ips=new FileInputStream(f);
	   InputStreamReader ipsr=new InputStreamReader(ips);
	   BufferedReader br=new BufferedReader(ipsr);
	   String ligne ;   
	   while((ligne=br.readLine())!=null){
	       String [] couper =ligne.split(" ") ; 
	       if(couper.length == 5 ) {
		   if(couper[0].charAt(0) == 'S') {
		       if(couper[1].charAt(0) == 'R') {
			   chargerSommet(true , couper) ; 
		       }
		       else if(couper[1].charAt(0) =='C') {
			   chargerSommet (false , couper) ; 
		       }
		   }
	       }
	       else if (couper.length == 6) {
		   if(couper[1].charAt(0) == 'L') 
		       chargerArete(false , couper) ;

	       }
	       else if (couper.length == 8) {
		   if(couper[1].charAt(0) == 'C') {
		       chargerArete (true , couper) ; 
		   }
	       }
	   }
	   br.close();
	}
	catch(Exception e)  {
	    System.out.println("erreur dans charger") ; 
	}
	
    }

    public void chargerSommet (boolean b , String [] couper ) {
	try {
	    double x = Double.parseDouble(couper[2]);
	    double y = Double.parseDouble(couper[3]) ; 
	    ajouterSommet(x,y,b) ; 
	    this.listeSom.get(compteurS-1).setNom(couper[4]);
	}
	catch(Exception e)  {
	    System.out.println("erreur dans sommet" +e.getMessage()) ;
	}
	
    } 

    public void chargerArete(boolean b , String[] couper) {
	try {
	    int taille = couper.length ; 
	    int position1 = Integer.parseInt(couper[taille-2]) ; 
	    int position2 = Integer.parseInt(couper[taille-1]);
	    Sommet un = this.listeSom.get(position1) ; 
	    Sommet deux= this.listeSom.get(position2) ; 
	    boolean pa = Boolean.parseBoolean(couper[3]) ; 
	    if(!b) {
		ajouterAreteL(un , deux ,pa) ; 		
	    }
	    else {
		double x = Double.parseDouble(couper[4]) ; 
		double y = Double.parseDouble(couper[5]) ; 
		ajouterAreteC (un , deux , x , y , pa) ; 
	    }
	    if(!pa){
		int p = Integer.parseInt(couper[2]) ; 
		this.listeAre.get(this.listeAre.size() -1 ).setPoid(p);
		this.listeAre.get(this.listeAre.size() -1).affichagePoids() ; 
	    }
	}
	catch(Exception e) {
	    System.out.println("erreur dans arete " +e.getMessage()) ;
	}
	
    }

    class temps {
	Timer t;
	File f ; 
	int type ; 
	int idCommence = -1 ; 
	public temps(int i ) {
	    t = new Timer();  
	    f = new File("tmp.txt") ; 
	    type = i ; 
	    stop = false; 
	}
    
	public void setIdCommence (int i) {
	    this.idCommence = i ; 
	}
	public void go() {
	    t.schedule(new MonAction(), 0, 1*1000); 
	
	}

	public void lireUneLigne (String [] couper ) {
	    try {
		if(couper.length == 3 ) {
		    int p = Integer.parseInt(couper[1]);
		    int c =  Integer.parseInt(couper[2]);
		    if(couper[0].charAt(0) == 'S') {
			listeSom.get(p).colorer(c) ; 
		    } 
		    else if (couper[0].charAt(0) == 'A') {
			listeAre.get(p).colorer(c) ; 
		    }
		    else {
			System.out.println("erreur 3  : " + couper.length + " couper[0] : " + couper[0] ) ; 
		    }
		}
		else if (couper.length == 1) {
		    if(couper[0].charAt(0) =='R') {
			initialiserCouleur(false) ; 
		    }
		    else if (couper[0] == "ALGO") {
			
		    }
		    else {
		    }
		}
	    }
	    catch (Exception e) {} 
	}


	class MonAction extends TimerTask {
	    InputStream ips; 
	    InputStreamReader ipsr;
	    BufferedReader br;
	    int position = 0 ; 
	    String [] chaine = new String [200]; 
	    
	    boolean fini = false ;
	    public MonAction ( ) {
		try {
		    ips=new FileInputStream(f); 
		    ipsr=new InputStreamReader(ips);
		    br=new BufferedReader(ipsr);
		    String ligne;
		    if(type == 2) {
			while((ligne=br.readLine())!=null){
			    chaine[position] = ligne ;
			    position ++ ; 
			}
			br.close();
		    }
		}
		catch (Exception e) {

		}
	    }


	    public void run() {
		if(stop) {
		    t.cancel() ; 
		}
		if(type == 0 ) 
		    BonSens() ; 
		if(type == 2 ) 
		    MauvaisSens() ; 
		if(type ==1 ) 
		    modif(); 
	    }
       
	    public void BonSens () {
		try {		
		    String ligne;
		    if((ligne=br.readLine())!=null){
			String [] couper = ligne.split(" "); 
			lireUneLigne(couper) ; 
		    
		    }
		    else {
			t.cancel();
			br.close(); 
		    }
		}
		catch (Exception e) {
		    System.out.println("erreur") ; 
		}
	    }

	    public void MauvaisSens() {
	        if (position != 0 ) {
		    position -- ;
		    String [] couper = chaine[position].split(" ");
		    lireUneLigne(couper);
		}
		else {
		    t.cancel();
		     
		}
	    }
	    
	    public void modif () {
		String ligne ;
		try {
		    while(!fini) {
		   
			ligne=br.readLine() ; 
			if(ligne == null) 
			    System.out.println("erreur") ; 
			else {
			    String [] couper = ligne.split(" "); 
			    lireUneLigne(couper) ; 
			    if(couper[0].charAt(0) == 'S') {
				int p = Integer.parseInt(couper[1]) ; 
				if (p == idCommence)
				    fini =true; 
			    }
			}
		    }
		    BonSens() ; 
		}
		catch(Exception e) {
		}
	    }
	}
    }
    

}
