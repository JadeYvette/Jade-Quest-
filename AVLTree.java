package cmsc420.meeshquest.part1;


//AVL Tree: Adjusted from the 
//https://rosettacode.org/wiki/AVL_tree#Java



public class AVLTree {

	  public Node root;
	  public int size = 0;
	  public int height = 0;
	    class Node {
	        public String key;
	        private int balance;
	        Node left;
			Node right;
			private Node parent;
			
	 
	        Node(String k, Node p) {
	            key = k;
	            parent = p;
	        }
	    }
	 
	    public boolean insert(String key) {
	        if (root == null)
	            root = new Node(key, null);
	        else {
	            Node n = root;
	            Node parent;
	            while (true) {
	                if (n.key == key)
	                    return false;
	 
	                parent = n;
	                boolean goLeft = greaterThan(n.key,key);
	                
	            
	                n = goLeft ? n.left : n.right;
	 
	                if (n == null) {
	                    if (goLeft) {
	                        parent.left = new Node(key, parent);
	                    } else {
	                        parent.right = new Node(key, parent);
	                    }
	                    rebalance(parent);
	                    break;
	                }
	            }
	        }
	         ++size;
	        return true;
	    }
	 
	    public void delete(String delCity) {
	        if (root == null)
	            return;
	        Node n = root;
	        Node parent = root;
	        Node delNode = null;
	        Node child = root;
	 
	        while (child != null) {
	            parent = n;
	            n = child;
	            child =  greaterThan(delCity,n.key)? n.right : n.left;
	            if (delCity == n.key)
	                delNode = n;
	        }
	 
	        if (delNode != null) {
	            delNode.key = n.key;
	 
	            child = n.left != null ? n.left : n.right;
	 
	            if (root.key == delCity) {
	                root = child;
	            } else {
	                if (parent.left == n) {
	                    parent.left = child;
	                } else {
	                    parent.right = child;
	                }
	                rebalance(parent);
	            }
	        }
	    }
	 
	    private void rebalance(Node n) {
	        setBalance(n);
	 
	        if (n.balance == -2) {
	            if (height(n.left.left) >= height(n.left.right))
	                n = rotateRight(n);
	            else
	                n = rotateLeftThenRight(n);
	 
	        } else if (n.balance == 2) {
	            if (height(n.right.right) >= height(n.right.left))
	                n = rotateLeft(n);
	            else
	                n = rotateRightThenLeft(n);
	        }
	 
	        if (n.parent != null) {
	            rebalance(n.parent);
	        } else {
	            root = n;
	        }
	    }
	 
	    private Node rotateLeft(Node a) {
	 
	        Node b = a.right;
	        b.parent = a.parent;
	 
	        a.right = b.left;
	 
	        if (a.right != null)
	            a.right.parent = a;
	 
	        b.left = a;
	        a.parent = b;
	 
	        if (b.parent != null) {
	            if (b.parent.right == a) {
	                b.parent.right = b;
	            } else {
	                b.parent.left = b;
	            }
	        }
	 
	        setBalance(a, b);
	 
	        return b;
	    }
	 
	    private Node rotateRight(Node a) {
	 
	        Node b = a.left;
	        b.parent = a.parent;
	 
	        a.left = b.right;
	 
	        if (a.left != null)
	            a.left.parent = a;
	 
	        b.right = a;
	        a.parent = b;
	 
	        if (b.parent != null) {
	            if (b.parent.right == a) {
	                b.parent.right = b;
	            } else {
	                b.parent.left = b;
	            }
	        }
	 
	        setBalance(a, b);
	 
	        return b;
	    }
	 
	    private Node rotateLeftThenRight(Node n) {
	        n.left = rotateLeft(n.left);
	        return rotateRight(n);
	    }
	 
	    private Node rotateRightThenLeft(Node n) {
	        n.right = rotateRight(n.right);
	        return rotateLeft(n);
	    }
	 
	    private int height(Node n) {
	        if (n == null)
	            return -1;
	        return 1 + Math.max(height(n.left), height(n.right));
	    }
	 
	    private void setBalance(Node... nodes) {
	        for (Node n : nodes)
	            n.balance = height(n.right) - height(n.left);
	    }
	 
	    public void printBalance() {
	        printBalance(root,0);
	    }
	 
	    private void printBalance(Node n, int level) {
	        if (n != null) {
	        	
	        	//System.out.printf("%s ", n.key);
	        	//System.out.printf("%s ", level);
	            printBalance(n.left,level + 1);
	            printBalance(n.right,level + 1);
	           
	            
	            
	        }else{
	        	 //System.out.print("emptynode ");
	        }
	        
	        if(level > height){
	        	this.height = level;
	        }
	    }
	 
	 
	    
	    
	    
	    
	    
	    
	    
	    
	    
	    
		public boolean greaterThan(String o1, String o2) {
			
			if(o1.compareTo(o2) < 0){
				return true;
			}
			
			return false;

			    
			}
			
			
		
		
		
		  public static void main(String[] args) {
		        AVLTree tree = new AVLTree();
		       String[] citiesIn = new String[5];
		       citiesIn[0] =   "B";
		       citiesIn[1] = "C" ;
		       citiesIn[2] = "A";
		       citiesIn[3] = "D";
		    		   
		        
		        for (int i = 0; i <= 3; i++)
		            tree.insert(citiesIn[i]);
		 
		       // System.out.print("Printing balance: ");
		        tree.printBalance();
		    }
		
		

	}

		
	    

	    
	    
	    
	    
	    
	    

