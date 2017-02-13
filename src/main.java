//AUTEUR : Navarna

class main {

    public static void main(String [] args ) {
	Graphe g = new Graphe () ; 
	g.ajouterSommet(10,60,true) ;
	g.ajouterSommet(90,40,false ) ;
	Sommet s = g.rechercheSommet(0) ;
	Sommet s1 = g.rechercheSommet(1) ; 
	g.ajouterArete(s,s1,20) ;
	g.affichage() ; 
    }
}
