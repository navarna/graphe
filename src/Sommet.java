//AUTEUR : Navarna
import javafx.scene.shape.* ; 
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent ; 
import javafx.event.*;
import javafx.scene.text.*; 
import javafx.scene.paint.*;

public abstract class Sommet  {
    private static int compteur = 0 ;
    private int id ; 
    private boolean type ;
    protected double X ; 
    protected double Y ;
    protected int taille ; 
    protected int nbrArete ; 
    protected String nom ; 
    protected boolean modif =false ; 
    protected boolean toucher = false; 
    protected Text text ; 
    protected int distanceAlgo = 0 ; 
    protected int sommetPrec = -1 ; 

    public Sommet (boolean t , double x , double y ,int t2 ) {
	this.id = this.compteur  ; 
	this.compteur ++;
	this.type  = t ; 
	this.X = x ; 
	this.Y = y ; 
	this.taille = t2 ;
	this.nbrArete = 0 ;
	this.nom = "Inconnu" ; 
	this.text = new Text() ; 
	this.text.setVisible(false)  ;
	Color r = Color.BLUE;
	this.text.setFill(r); 
	
    }

    public int getId (){
	return this.id ; 
    } 
    public void reset () {
	this.compteur = 0 ; 
    }
    public boolean getType (){
	return this.type ; 
    }
    
    public void setType (boolean t ) {
	this.type = t ; 
    }
    
    public double getX () {
	return this.X ; 
    }

    public void setX (double x ){
	this.X =x ;  
    }

    public void setY (double y ) {
	this.Y  = y ; 
    }

    public void setNom (String n) {
	this.nom = n ; 
    }

    public String getNom () {
	return this.nom ; 
    }

    public double getY () {
	return this.Y ; 
    }

    public int getNbrArete () {
	return this.nbrArete ; 
    }

    public void setNbrArete (int na) {
	this.nbrArete = na ; 
    }

    public void setModif(boolean b)  {
	this.modif = b; 
    }
    public boolean getModif () {
	return modif ; 
    }

    public void setToucher(boolean b) {
	this.toucher = b ; 
    }
    public boolean getToucher () {
	return this.toucher ; 
    }

    public void modifieArete(boolean b ) {
	if(b) 
	    this.nbrArete ++ ;
	else 
	    this.nbrArete -- ; 
    }
    
    public String toString () {
	String affichage = "id : " + this.id  +"NbrArete : " +this.nbrArete+ " type : " + this.type + " X : " + this.X + " Y: " +this.Y + "sommet precedent  : " + this.sommetPrec + "  , distance de l'algo " + this.distanceAlgo ; 
	return affichage ; 
    }

    public void information (boolean b) {
	if(b ) {
	    informationAjour() ; 
	    this.text.setVisible(b) ; 
	}
	else 
	    this.text.setVisible(b) ;
	    
    }
    public void informationAjour () {
	this.text.setText("id : "+this.id +"\nnom : "+this.nom + "\nnbrArete : " + this.nbrArete);
    }

    public void setDistanceAlgo( int d ) {
	this.distanceAlgo = d ; 
    }
    public int getDistanceAlgo () {
	return this.distanceAlgo ; 
    }

    public void setSommetPrec (int sp) {
	this.sommetPrec = sp ; 
    }
    
    public int getSommetPrec () {
	return this.sommetPrec  ; 
    }

    public String sauverGene() {
	String sauve = " " + this.X +" "+ this.Y  + " "+ nom ;
	return sauve ; 
    }

    public abstract void positioner () ;
    public abstract void colorer (int c) ;

    public abstract String sauver() ; 
    
}

class Rec extends Sommet {
    public Rectangle sh ; 

    public Rec (double x , double y , int t2) {
	super (true , x , y , t2) ;
	sh = new Rectangle () ;
	Color r = Color.YELLOW ; 
	sh.setFill(r) ; 
    }

    public void positioner () {
	this.sh.setX(this.X-5) ;
	sh.setY(this.Y-5) ; 
	sh.setWidth(this.taille);
	sh.setHeight(this.taille);
	this.text.setX(this.X-10) ; 
	this.text.setY(this.Y+20) ; 
	
    }

    public void colorer (int c) {
        Color r =Color.YELLOW;  
	if(c==0) 
	    r = Color.BLACK ;
	else if(c ==1 ) 
	    r = Color.RED ; 
	else if(c == 2) 
	    r = Color.BLUE;
	else if(c ==3)
	    r = Color.GREEN ;
	sh.setFill(r) ; 
		
    }

    public String sauver () {
	String sauve = "S R" + sauverGene() ; 
	return sauve ; 
    } 
}

class Cir extends Sommet {
    public Circle sh ; 
    public Cir (double x , double y , int t2) {
	super (false ,x, y, t2);
	sh = new Circle()  ; 
	setLesAction() ; 
	Color r = Color.RED ; 
	sh.setFill(r) ; 
    }
    
    public void positioner () {
	sh.setCenterX(this.X) ; 
	sh.setCenterY(this.Y) ;
	sh.setRadius(this.taille/2) ; 
	this.text.setX(this.X-10) ; 
	this.text.setY(this.Y+20) ; 

	    
    }

    public void setLesAction () {

	sh.setOnMouseClicked (new EventHandler<MouseEvent>() {
		public void handle (MouseEvent event ) {
		    if(modif) 
		        toucher = true ; 
		}
	    });
        
    }
    public void colorer (int c) {
        Color r =Color.YELLOW;  
	if(c==0) 
	    r = Color.BLACK ;
	else if(c ==1 ) 
	    r = Color.RED ; 
	else if(c == 2) 
	    r = Color.BLUE;
	else if(c ==3)
	    r = Color.GREEN ;
	sh.setFill(r) ; 
		
    }

    public String sauver () {
	String sauve = "S C" + sauverGene() ; 
	return sauve ; 
    } 

}
