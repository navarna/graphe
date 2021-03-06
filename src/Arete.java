//AUTEUR : Navarna
import javafx.scene.shape.* ;
import javafx.scene.text.*; 
import javafx.scene.paint.*;
import javafx.event.*;
import javafx.scene.input.MouseEvent ; 

public abstract class  Arete{
    protected int[] sommet ; 
    protected int poid ; 
    protected boolean type; 
    protected boolean toucher ;
    protected boolean demandeSupression ; 
    protected boolean poidsAuto ; 
    public Text text ; 

    public Arete (int un , int deux  , boolean t, boolean pA ) {
	this.sommet =new int [2] ; 
	this.poid = 0 ; 
	if(un < deux ){
	    this.sommet[0] = un ;
	    this.sommet[1] = deux ; 
	} 
	else  {
	    this.sommet[0] = deux ;
	    this.sommet[1] = un ; 
	}
	this.type = t ; 
	this.toucher = false ; 
	this.demandeSupression = false ; 
	this.text = new Text(""+this.poid);    
	this.poidsAuto = pA  ;
	this.poid = 0 ;
    }

    public boolean estUnSommet (Sommet s) {
	if ((s.getId() == this.sommet[0] ) || (s.getId() == this.sommet[1]))
	    return true ; 
	return false ; 
	
    }

    public boolean estUnSommet (int i ) {
	if((i == this.sommet[0])||(i== this.sommet[1]))
	    return true; 
	return false ;
    }


    public int getSommet (int i ) {
	return this.sommet[i] ; 
    }

    public void setSommet (int i , int v ) {
	this.sommet[i] = v ; 
    }

    public int getPoid() {
	return this.poid; 
    }
    public void setPoid (int p ) {
	this.poid = p ; 
    }

    public void setPoidAuto (boolean b ) {
	this.poidsAuto = b ; 
    }

     public boolean setPoidAuto () {
	return this.poidsAuto ; 
    }
    
    public String toString () {
	String affichage  = "debut : " + this.sommet[0] + " fin : " + this.sommet[1] + " poids : " + this.poid ; 
	return affichage ; 
	
    }

    public boolean getType () {
	return this.type  ; 
    }

    public void setType (boolean t) {
	this.type = t ; 
    }

    public boolean getToucher () {
	return this.toucher ; 
    }

    public void setToucher (boolean b){
	this.toucher = b ; 
    } 
    
    public void setDemandeSupression (boolean d) {
	this.demandeSupression = d ; 
    }

    public void textNoir() {
	Color r = Color.BLACK;
	text.setFill(r); 
    }

    public int poids (double x1 , double y1 , double x2 , double y2) {
	if(!poidsAuto) return this.poid ; 
	int distance =(int)  Math.sqrt( (x2 - x1)*(x2-x1) + (y2 - y1)*(y2-y1)) ;
	return distance/10 ; 

    }

    public abstract void positioner (double x1 , double y1 , double x2 , double y2 ) ;
    public abstract boolean appartientAreteXY(double x , double y ) ;
    public abstract void deplacer (double x1 , double y1 , double x2 , double y2 ) ;
    public abstract void colorer (int c ) ; 
    public abstract String sauver () ; 

    public void affichagePoids () {
	this.text.setText(""+this.poid); 
    }

    public int autreSommet (int s) {
	if(this.sommet[0] == s) {
	    return this.sommet[1]; 
	}
	else 
	    return this.sommet[0] ; 
    }

    public String sauverGene() {
	String sauve = " " + this.poid + " " + this.poidsAuto ; 
	return sauve; 
    }
}

class Ligne extends Arete {
    Line l ; 

    public Ligne (int un , int deux , boolean pA ) {
	super (un , deux  ,false , pA) ;
	this.l = new Line();
	ajouterLesAction() ; 

    }
    
    public void positioner (double x1 , double y1 , double x2 , double y2 ) {
	this.l.setStartX(x1 );
	this.l.setStartY(y1);
	this.l.setEndX(x2);
	this.l.setEndY(y2);
	this.poid = poids(x1,y1,x2,y2);

	double xtext , ytext ; 
	xtext = (x1+x2) / 2 ;
	ytext = (y1 +y2) / 2;
	this.text.setX(xtext) ; 
	this.text.setY(ytext) ; 
	this.text.setText(""+this.poid);
    }

    public boolean appartientAreteXY (double x , double y ) {
	double a  = (this.l.getEndY() - this.l.getStartY() )/ (this.l.getEndX()- this.l.getStartX() ) ;
	double b = (this.l.getEndX() * this.l.getStartY () -this.l.getStartX() *this.l.getEndY() ) / (this.l.getEndX() - this.l.getStartX() ) ; 
	double yCorrect = a*x+b ;
	if(( yCorrect -2<= y)&&(yCorrect +2 >= y)) 
	    return true ; 
	return false ; 
    } 

    public void deplacer (double x1 , double y1 , double x2 , double y2) {
	if((this.l.getStartX() == x1 ) &&(this.l.getStartY() == y1)){
	    positioner(x2,y2, this.l.getEndX() , this.l.getEndY() ) ; 
	}
	else if ((this.l.getEndX() == x1 ) &&(this.l.getEndY() == y1)){
	    positioner( this.l.getStartX() , this.l.getStartY(),x2,y2 ) ; 
	}
	else {
	    System.out.println("erreur") ; 
	}
    }

    public void ajouterLesAction () {
	l.setOnMousePressed(new EventHandler<MouseEvent>() {
		    public void handle (MouseEvent event ) {
			Color r = Color.RED;
			text.setFill(r); 
		    }
	    });
	l.setOnMouseReleased(new EventHandler<MouseEvent>() {
		    public void handle (MouseEvent event ) {
			if(demandeSupression) 
			    toucher = true ; 
			else {
			    Color r = Color.BLACK;
			    text.setFill(r) ; 
			}
		    }
	    });
	text.setOnMousePressed(new EventHandler<MouseEvent>() {
		public void handle (MouseEvent event ) {
		    Color r = Color.RED;
		    l.setStroke(r); 
		}
	    });

	text.setOnMouseReleased(new EventHandler<MouseEvent>() {
		    public void handle (MouseEvent event ) {
			if(demandeSupression) 
			    toucher = true ; 
			else {
			    Color r = Color.BLACK;
			    l.setStroke(r) ; 
			}
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
	l.setStroke(r) ; 
		
    }

    public String sauver () {
	String sauve = "A L" + sauverGene()  ; 
	return sauve ; 
    }
}


class Courbe extends Arete {
    Path l ;
    MoveTo mv ; 
    QuadCurveTo qd ; 

    public Courbe ( int un , int deux , boolean pA ) {
	super (un , deux  , true , pA) ; 
        this.l = new Path() ; 
	this.mv= new MoveTo() ; 
	this.qd = new QuadCurveTo () ;
	this.l.getElements().addAll(mv,qd); 
	ajouterLesAction() ; 
    }

    public void positioner (double x1 , double y1 , double x2 , double y2 ) { 
	this.mv.setX(x1) ;
	this.mv.setY(y1);
	this.qd.setX(x2) ;
	this.qd.setY(y2) ;
	this.poid = poids(x1,y1,x2,y2);
	this.text.setText(""+this.poid);
	double xtext , ytext ; 
	xtext = (x1+x2) / 2 ;
	ytext = (y1 +y2) / 2;
	this.text.setX(xtext) ; 
	this.text.setY(ytext) ;
    }

    public void positionerCourbe (double x , double y ) {
	this.qd.setControlX(x) ; 
	this.qd.setControlY(y) ; 
	this.text.setX(x) ; 
	this.text.setY(y) ; 
    }

    public boolean appartientAreteXY(double x , double y ) {return false ; }
    public void deplacer (double x1 , double y1 , double x2 , double y2 ) {
	if((x1 == this.mv.getX() )&&(y1 == this.mv.getY() )){
	    double xc = (-mv.getX() + x2) + qd.getControlX() ; 
	    double yc = (-mv.getY() +y2) + qd.getControlY()  ; 
	    
	    positioner(x2,y2,qd.getX() , qd.getY()) ;
	    positionerCourbe(xc,yc) ; 
	}
	else if( (x1 == this.qd.getX())&&(y1 == this.qd.getY())){
	    double xc = (-qd.getX() + x2)/2 + qd.getControlX() ; 
	    double yc = (-qd.getY() +y2)/2 + qd.getControlY()  ; 
	    positioner(mv.getX() , mv.getY() , x2,y2);
	     positionerCourbe(xc,yc) ; 
	}
    }
    public void ajouterLesAction () {
	l.setOnMousePressed(new EventHandler<MouseEvent>() {
		    public void handle (MouseEvent event ) {
			Color r = Color.RED;
			text.setFill(r) ; 
		    }
	    });
	l.setOnMouseReleased(new EventHandler<MouseEvent>() {
		    public void handle (MouseEvent event ) {
			if(demandeSupression) 
			    toucher = true ; 
			else {
			    Color r = Color.BLACK;
			    text.setFill(r) ; 
			}
		    }
	    });
	text.setOnMousePressed(new EventHandler<MouseEvent>() {
		public void handle (MouseEvent event ) {
		    Color r = Color.RED;
		    l.setStroke(r); 
		}
	    });

	text.setOnMouseReleased(new EventHandler<MouseEvent>() {
		    public void handle (MouseEvent event ) {
			if(demandeSupression) 
			    toucher = true ; 
			else {
			    Color r = Color.BLACK;
			    l.setStroke(r) ; 
			}
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
	l.setStroke(r) ; 
		
    }
    
    public String sauver () {
	String sauve = "A C" + sauverGene() + " "  +this.qd.getControlX() + " "+ this.qd.getControlY(); 
	return sauve ; 
    }

}
