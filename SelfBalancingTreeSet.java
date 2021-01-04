
import java.util.Comparator;
//** This will be my implementation of an AVL tree */
public class SelfBalancingTreeSet<K> extends TreeSet<K> {
    //your implementation goes here
    //** Constructs an empty set using the natural ordeering of keys */
    public SelfBalancingTreeSet(){super();}
    //** Constructs an empty set using the given comparator to order keys */
    public SelfBalancingTreeSet(Comparator<K> comp) {super(comp);}

    //** Returns the height of the given tree Position */
    protected int height(Position<K> p){
        return tree.getAux(p);
    }
    //** Recomputes the height of the given position based on its children's height */
    protected void recomputeHeight(Position<K> p){
        tree.setAux(p, 1 + Math.max(height(left(p)), height(right(p))));
    }
    //** Returns whether a position has balance factor between -1 and 1 inclusive */
    protected boolean isBalanced(Position<K> p){
        return Math.abs(height(left(p)) - height(right(p))) <=1;
    }

    //** returns a child of p with the tallest height */
    protected Position<K> tallerChild(Position<K> p){
        if(height(left(p)) > height(right(p))) return left(p);
        if(height(left(p)) < height(right(p))) return right(p);
        //equal height children go with child matching parent's orientation
        if(isRoot(p)) return left(p);
        if(p == left(parent(p)))return left(p);
        else return right(p);   
    }

    //Utility used to rebalance an insert or removal. Traverses the path upward from p, performing a trinode restructuring when imbalance is found
    protected void rebalance(Position<K> p){
        int oldHeight, newHeight;
        do{
            oldHeight = height(p);
            if(!isBalanced(p)){
                //perform a trinode restructuring, setting p to root 
                //recompute new local heights after the restructuring
                p = restructure(tallerChild(tallerChild(p)));
                recomputeHeight(left(p));
                recomputeHeight(right(p));
            }
            recomputeHeight(p);
            newHeight = height(p);
            p = parent(p);
        } while(oldHeight != newHeight && p!= null);
    }
    
    protected void rebalanceInsert(Position<K> p){
        rebalance(p);   
    }
    protected void rebalanceDelete(Position<K> p){
        if(!isRoot(p)){
            rebalance(parent(p));
        }
    }
    //protected void rebalanceAccess(Position<K> p){}

    
}
