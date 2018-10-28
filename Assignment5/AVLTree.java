import java.util.*;
public class AVLTree{

    Node root=null;
    public AVLTree(Node Newroot){
        root=Newroot;
    }
    public AVLTree(){
        root=null;
    }
    public Node getRoot(){
        return this.root;
    }
    public Node findNode(Node node,Node sroot){
        Node n1=new Node();
        Node n2=new Node();
        if(sroot==null) return null;
        if(sroot.equals(node)) return sroot;
        else{
            if(node.compareTo(sroot)>0) n1=findNode(node,sroot.right);
            else n2=findNode(node,sroot.left);
        }
        if(n1!=null) return n1;
        else if(n2!=null) return n2;
        else return null;
    }
    public Node findWordInTree(String s,Node sroot){
        Node n1=new Node();
        Node n2=new Node();
        if(sroot==null) return null;
        if(sroot.p.word.equals(s)) return sroot;
        else{
            n1=findWordInTree(s,sroot.right);
            n2=findWordInTree(s,sroot.left);
        }
        if(n1!=null) return n1;
        else if(n2!=null) return n2;
        else return null;
    }

    public Node getSuccessor(Node n){
        Node n1=findNode(n,this.root);
        if(n1.right==null){
            Node it=n1.parent;
            if(it.left.equals(n1)) return it;
            else {it=it.parent; n1=n1.parent;}
        }
        else{
            n1=n1.right;
            Node it=n1.left;
            while(it==null){
                n1=it;
                it=it.left;
            }
            return n1;
        }
        return null;
    }
    public Node findExitNode(Node n){
        int flag=1;
        Node curr=root;
        Node prev=root;
       
            while(flag==1){           
                if(n.compareTo(curr)>0) {prev=curr; curr=curr.right;}
                else {prev=curr; curr=curr.left;}
                if(curr==null) flag=0;
            }
            return prev;
    }
    public void insertNode(Node node){
      
        Node curr=node;
        if(root==null){
            root=node;
        }
        else{
            Node addHere=findExitNode(node);
            if(addHere.compareTo(node)>0) addHere.left=node;
            else addHere.right=node;
            node.parent=addHere;
            //while loop-->increase height till parent, check at every node
            while(curr!=null){
                if((curr.left==null)&&(curr.right==null)) curr.height=0;
                
                else{
                    if((curr.right==null)||(curr.left==null)){
                        if(curr.right==null) curr.height=curr.left.height+1;
                        else curr.height=curr.right.height+1;
                    }
                    else if (curr.right.height>curr.left.height) curr.height=curr.right.height+1;
                    else curr.height=curr.left.height+1;
                    /*if((curr.right==null)||(curr.left.height>curr.right.height)) curr.height=curr.left.height+1;
                    else if((curr.left==null)||(curr.right.height>curr.left.height)) curr.height=curr.right.height+1;
                    else curr.height=0;*/
                    if((curr.left==null)||(curr.right==null)){
                        if((curr.left==null)&&(curr.right.height>0)) problemDetected(curr);
                        else if((curr.right==null)&&(curr.left.height>0)) problemDetected(curr);
                        else ;
                    }
                    else{
                       
                        if(Math.abs(curr.right.height-curr.left.height)>1) problemDetected(curr);
                    }
                }
                curr=curr.parent;
                
            }
        }
        
    }
    public void problemDetected(Node node){
      
        int flag=0;
        Node z=node;
        Node y=new Node();
        Node x=new Node();
        Node par=new Node();
        if(node.right==null){
            y=node.left;
            if((y.left==null)||(y.right==null)){
                if(y.left==null) {x=y.right; flag=2;}
                else {x=y.left; flag=1;}
            }
            else{
                if(y.left.height>y.right.height){x=y.left; flag=1;}
                else{x=y.right; flag=2;}
            }
        }
        else if(node.left==null){
            y=node.right;
            if((y.left==null)||(y.right==null)){
                if(y.left==null) {x=y.right; flag=4;}
                else {x=y.left; flag=3;}
            }
            else{
                if(y.left.height>y.right.height){x=y.left; flag=3;}
                else{x=y.right; flag=4;}
            }
        }
        else{
            if(node.left.height>node.right.height){
                y=node.left;
                if((y.left==null)||(y.right==null)){
                    if(y.left==null) {x=y.right; flag=2;}
                    else {x=y.left; flag=1;}
                }
                else{
                    if(y.left.height>y.right.height){x=y.left; flag=1;}
                    else{x=y.right; flag=2;}
                }
            }
            else{
                y=node.right;
                if(((y.right==null)&&(y.left!=null))||(y.left.height>=y.right.height)){
                    x=y.left;
                    flag=3;
                }
                else{
                    x=y.right;
                    flag=4;
                }
            }
        }
        /*if(((node.right==null)&&(node.left!=null))||(node.left.height>node.right.height)){
            y=node.left;
            if((y.left==null)||(y.right==null)){
                if(y.left==null) {x=y.right; flag=2;}
                else {x=y.left; flag=1;}
            }
            else{
                if(y.left.height>y.right.height){x=y.left; flag=1;}
                else{x=y.right; flag=2;}
            }
        }
        else{
            y=node.right;
            if(((y.right==null)&&(y.left!=null))||(y.left.height>=y.right.height)){
                x=y.left;
                flag=3;
            }
            else{
                x=y.right;
                flag=4;
            }
        }*/
if(z.parent==null){
                
                Node fakeNode=new Node();
                fakeNode.left=z;
                fakeNode.right=z;
                fakeNode.height=-1;
                fakeNode.p=null;
                par=fakeNode;
            }
            else{
                par=z.parent;
            }
            if(z.right==null){
                    Node fn=new Node();
                    fn.left=null;
                    fn.right=null;
                    fn.height=-1;
                    fn.parent=z;
                    z.right=fn;
                }
                if(y.right==null){
                    Node fn=new Node();
                    fn.left=null;
                    fn.right=null;
                    fn.height=-1;
                    fn.parent=y;
                    y.right=fn;
                }
                if(x.right==null){
                    Node fn=new Node();
                    fn.left=null;
                    fn.right=null;
                    fn.height=-1;
                    fn.parent=x;
                    x.right=fn;
                }if(z.left==null){
                    Node fn=new Node();
                    fn.left=null;
                    fn.right=null;
                    fn.height=-1;
                    fn.parent=z;
                    z.left=fn;
                }
                if(y.left==null){
                    Node fn=new Node();
                    fn.left=null;
                    fn.right=null;
                    fn.height=-1;
                    fn.parent=y;
                    y.left=fn;
                }
                if(x.left==null){
                    Node fn=new Node();
                    fn.left=null;
                    fn.right=null;
                    fn.height=-1;
                    fn.parent=x;
                    x.left=fn;
                }

        switch(flag){
            case 1 :{
                if(par.compareTo(z)>0){par.left=y; y.parent=par;z.left=y.right; y.right.parent=y; y.right=z;z.parent=y;}
                else{par.right=y; y.parent=par;z.left=y.right; y.right.parent=y; y.right=z;z.parent=y;}
                x.height=Math.max(x.right.height,x.left.height)+1; z.height=Math.max(z.left.height,z.right.height)+1; y.height=Math.max(y.left.height,y.right.height)+1;
                if(par.height==-1){
                    this.root=y;
                    y.parent=null;
                   
                }
                break;
            }
            case 2 :{
                if(par.compareTo(z)>0){par.left=x; x.parent=par; z.left=x.right; z.left.parent=z; y.right=x.left; y.right.parent=y; x.left=y; y.parent=x;x.right=z;z.parent=x;}
                else{par.right=x; x.parent=par; z.left=x.right; x.right.parent=x; y.right=x.left; y.right.parent=y;x.right=z; x.left=y; z.parent=x; y.parent=x;}
                x.height=Math.max(x.right.height,x.left.height)+1; z.height=Math.max(z.left.height,z.right.height)+1; y.height=Math.max(y.left.height,y.right.height)+1;
                if(par.height==-1){
                    this.root=x;
                    x.parent=null;
                }
                break;
            }
            case 3 :{
                if(par.compareTo(z)>0){par.left=x;x.parent=par; y.left=x.right; y.right.parent=y;z.right=x.left;z.right.parent=z;x.right=y;y.parent=x;x.left=z;z.parent=x; }
                else{par.right=x;x.parent=par; y.left=x.right; y.right.parent=y;z.right=x.left;z.right.parent=z;x.right=y;y.parent=x;x.left=z;z.parent=x; }
                x.height=Math.max(x.right.height,x.left.height)+1; z.height=Math.max(z.left.height,z.right.height)+1; y.height=Math.max(y.left.height,y.right.height)+1;
                if(par.height==-1){
                    this.root=x;
                    x.parent=null;
                   
                }
                break;
            }
            case 4 :{
                if(par.compareTo(z)>0){par.left=y;y.parent=par; z.right=y.left; z.right.parent=z; y.left=z; z.parent=y;}
                else{par.right=y;y.parent=par; z.right=y.left; z.right.parent=z; y.left=z; z.parent=y;}
                x.height=Math.max(x.right.height,x.left.height)+1; z.height=Math.max(z.left.height,z.right.height)+1; y.height=Math.max(y.left.height,y.right.height)+1;
                if(par.height==-1){
                    this.root=y;
                    y.parent=null;
                   
                }
                break;
            }
        }
        if(x.right.height==-1){
            x.right=null;
          
        }
        if(x.left.height==-1){
            x.left=null;
          
        }
        if(y.right.height==-1){
            y.right=null;
           
        }
        if(y.left.height==-1){
            y.left=null;
            
        }
        if(z.right.height==-1){
            z.right=null;
           
        }
        if(z.left.height==-1){
            z.left=null;
           
        }
        
    }
    public void printTree(Node root){
        if(root==null) return;
        else {
            printTree(root.left);
            System.out.print(root.p.wordIndex+", ");
            printTree(root.right);
        }
    }
    public Node returnRoot(){
        return this.root;
    }
}
class Node {
    public Position1 p;
    public Node left=null;
    public Node right=null;
    public Node parent=null; 
    public int height=0;
    public Node( Position1 pa){
        this.p=pa;
    }
    public Node(){}
    public boolean equals(Node n){
        return this.p.equals(n.p);
    }
    public int compareTo(Node n){
        if(this.p==null) return 1;
        return p.compareTo(n.p);
    }
    
}
class Position1{
    PageEntry p;
    int wordIndex;
    String word;
    public PageEntry getPageEntry(){
        return p;
    }
    public int getWordIndex(){
        return wordIndex;
    }
    public boolean equals( Position1 p){
        return ((p.equals(p.p))&&(wordIndex==p.wordIndex));
    }
    public int compareTo( Position1 p){
        return (wordIndex - p.wordIndex);
    }   
}