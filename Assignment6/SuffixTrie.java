import java.util.*;
import  java.util.Scanner;
import java.io.FileReader;
import java.io.IOException;
import java.io.File;
import java.lang.*;
import java.io.*;
import java.io.BufferedReader;

public class SuffixTrie{
    class Node{
    }
    class IntNode extends Node{
        Edge[] children =new Edge[256];
        public IntNode(){
            for(int h=0;h<256;h++){
                children[h]= new Edge();
            }
        }
    }

    class LeafNode extends Node{
        int beginIndex;
    }
    class Edge{
        char c;
        Node node;
        int used=0;
    }

    IntNode root=new IntNode();

    public SuffixTrie(){
        IntNode root=new IntNode();
    }
    public void makeSuffixTrie(String str){
        str+='$';
        for(int i=str.length()-1; i>=0; i--){
            String current= str.substring(i,str.length());
            IntNode nod=root;
            char c;
                for(int n=0;n<current.length(); n++){
                    c=current.charAt(n);
                    if(nod.children[(int) c].used==0 ){  
                        for(int k=n;k<current.length();k++){
                            c=current.charAt(k);
                            if(c=='$'){
                                nod.children[(int)c].c=c;
                                nod.children[(int)c].used=1;
                                LeafNode lf=new LeafNode();
                                lf.beginIndex=i;
                                nod.children[(int) c].node=lf;
                            }
                           else{
                                Edge temp=new Edge();
                                temp.c=c;
                                nod.children[(int) c]=temp;
                                nod.children[(int)c].used=1;
                                IntNode tem=new IntNode();
                                nod.children[(int) c].node=tem;
                                nod=(IntNode) nod.children[(int) c].node;
                            }
                        }
                        break;
                    }
                    else if(nod.children[(int) c ].c==(c)) {
                        nod=(IntNode) nod.children[(int) c ].node;
                    }
                    else{
                        /*for(int k=n;k<current.length();k++){
                            c=current.charAt(k);
                            if(c=='$'){
                                nod.children[(int) c].c=c;
                                LeafNode lf=new LeafNode();
                                lf.beginIndex=i;
                                nod.children[(int) c].node=lf;
                            }
                           else{
                                nod.children[(int) c].c=c;
                                nod=(IntNode) nod.children[(int) c].node;
                            }
                        }*/
                    }
                }
        }
    }

    public int isSubstring(String s){
        int flag1=0;
        for(int o=0;o<256;o++){
           if(this.root.children[o].used==1) flag1=1;
        }
        if(flag1==0) return -1;
        int flag=1;
        IntNode nd=root; 
        for(int i=0;i<s.length();i++){
            char c=s.charAt(i);
            if(nd.children[(int) c].c==c) nd=(IntNode) nd.children[(int)c].node;
            else {flag=0; break;}

        }
        return flag;
    }

    public int numSubstrings(String s){
        int flag1=0;
        for(int o=0;o<256;o++){
           if(this.root.children[o].used==1) flag1=1;
        }
        if(flag1==0) return -1;
        int flag=1;
        IntNode nd=root; 
        int i;
        for( i=0;i<s.length();i++){
            char c=s.charAt(i);
            if(nd.children[(int) c].c==c) nd=(IntNode) nd.children[(int)c].node;
            else {flag=0; break;}
        }
        if(i==s.length())
        return countLeaf(nd);
        else return 0;
       
    }
    public int numSubstrings(String s, IntNode root){
        int flag1=0;
        for(int o=0;o<256;o++){
           if(root.children[o].used==1) flag1=1;
        }
        if(flag1==0) return -1;
        int flag=1;
        IntNode nd=root; 
        int i;
        for( i=0;i<s.length();i++){
            char c=s.charAt(i);
            if(nd.children[(int) c].c==c) nd=(IntNode) nd.children[(int)c].node;
            else {flag=0; break;}
        }
        if(i==s.length())
        return countLeaf(nd);
        else return 0;
       
    }

    public int numFuzzySubstrings(String s,int num){
        int flag1=0;
        for(int o=0;o<256;o++){
           if(this.root.children[o].used==1) flag1=1;
        }
        //int allowed=0;
        if(flag1==0) return -1;
        else return someName(s,num,this.root);
        
    }
    public int someName(String s, int num, IntNode n){
        int rtrn=0;
        if(s.length()==0) {return countLeaf(n);}
        else if(num==0) {return numSubstrings(s,n);} 
        else{
            char c = s.charAt(0);
            for(int i=0;i<256;i++){
                if(i==36) continue;
                if(n.children[i].used==1){
                    if(i==(int)c){
                        String sd=s.substring(1);
                        rtrn+=someName(sd,num, (IntNode)n.children[(int)c].node);
                    }
                    else{
                        String sd=s.substring(1);
                        int numd =num-1;
                        rtrn+=someName(sd,numd,(IntNode)n.children[i].node);
                    }   
                }
            }
           /* if(n.children[(int) c].used==1) {
                s=s.substring(1);
                rtrn+=someName(s,num, (IntNode)n.children[(int)c].node);
                //rtrn+=someName(s,num,n);
            }
            else{
                s=s.substring(1);
                num--;
                for(int i=0;i<256;i++){
                    if(i==36) {continue;}
                    if(n.children[i].used==1){
                        rtrn+=someName(s,num,(IntNode)n.children[i].node);
                    }
                }
            }*/
        }
        return rtrn;
    }
    
    public int numSubstringsNotOverlap(String s){
        int flag1=0;
        Vector<Integer> vec;
        for(int o=0;o<256;o++){
           if(this.root.children[o].used==1) flag1=1;
        }
       if(flag1==0) return -1;
        int flag=1;
        IntNode nd=root; 
        int i;
        for(i=0;i<s.length();i++){
            char c=s.charAt(i);
            if(nd.children[(int) c].c==c) nd=(IntNode) nd.children[(int)c].node;
            else {flag=0; break;}
        }
        if(i==s.length()){
            vec= getPositions(nd);
            Collections.sort(vec);
            for(int j=0;j<vec.size()-1;j++){
                if(vec.get(j+1)-vec.get(j)<s.length()) vec.remove(j);
            }
            return vec.size();
        }else return 0;
    }

    public Vector getPositions(IntNode n){
        int num=0;
        Vector<Integer> vec = new Vector<Integer>();
        for(int i=0;i<256;i++){
            if(i==36){
                if(n.children[36].used==1) {
                    LeafNode lf=(LeafNode) n.children[36].node;
                    vec.addElement(lf.beginIndex);
                }
            }
            else if(n.children[i].used==1){
                Vector<Integer> v=new Vector<Integer>();
                v.addAll(vec);
                v.addAll(getPositions((IntNode)n.children[i].node));  
                vec=v;
            }
            else continue;
        }
        return vec;
    }
    public int countLeaf(IntNode n){
        int num=0;
        for(int i=0;i<256;i++){
            if(i==36){
                if(n.children[36].used==1) num++;
            }
            else if(n.children[i].used==1){
                num+=countLeaf((IntNode)n.children[i].node);
            }
            else continue;
        }
        return num;
    }
    public Vector posSubstrings(String s){
        int flag1=0;
        Vector<Integer> vec;
        for(int o=0;o<256;o++){
           if(this.root.children[o].used==1) flag1=1;
        }
       if(flag1==0) return null;
        int flag=1;
        IntNode nd=root; 
        int i;
        for(i=0;i<s.length();i++){
            char c=s.charAt(i);
            if(nd.children[(int) c].c==c) nd=(IntNode) nd.children[(int)c].node;
            else {flag=0; break;}
        }
        if(i==s.length()){
            vec= getPositions(nd);
            Collections.sort(vec);
            return vec;
        }else return null;
    }
public Vector posSubstrings(String s, IntNode root){
        int flag1=0;
        Vector<Integer> vec;
        for(int o=0;o<256;o++){
           if(root.children[o].used==1) flag1=1;
        }
       if(flag1==0) return null;
        int flag=1;
        IntNode nd=root; 
        int i;
        for(i=0;i<s.length();i++){
            char c=s.charAt(i);
            if(nd.children[(int) c].c==c) nd=(IntNode) nd.children[(int)c].node;
            else {flag=0; break;}
        }
        if(i==s.length()){
            vec= getPositions(nd);
            Collections.sort(vec);
            return vec;
        }else return null;
    }
    public Vector posFuzzySubstrings(String s, int num){
        int flag1=0;
        Vector<Integer> vec;
        for(int o=0;o<256;o++){
           if(this.root.children[o].used==1) flag1=1;
        }
       if(flag1==0) return null;
       Vector<Integer> pozfuzzy=new Vector<Integer>();
       pozfuzzy=someName1(s,num,this.root);
       return pozfuzzy;
    }

    public Vector posFuzzySubstringsNotOverlap(String s, int num){
        int flag1=0;
        Vector<Integer> vec;
        for(int o=0;o<256;o++){
           if(this.root.children[o].used==1) flag1=1;
        }
        if(flag1==0) return null;
        Vector<Integer> pozfuzzy=new Vector<Integer>();
        pozfuzzy=someName1(s,num,this.root);
        Collections.sort(pozfuzzy);
        for(int j=0;j<pozfuzzy.size()-1;j++){
            if(pozfuzzy.get(j+1)-pozfuzzy.get(j)<s.length()) pozfuzzy.remove(j);
        }
        return pozfuzzy;
    }

    public Vector someName1 (String s, int num, IntNode n){
        Vector<Integer> rtrn=new Vector<Integer>();
        if(s.length()==0) return getPositions(n);
        else if(num==0) {
            Vector<Integer> nothing=posSubstrings(s,n); 
            if(nothing==null){
                nothing=new Vector<Integer>();
            }
            return nothing;
        } 
        else{
            char c = s.charAt(0);
            for(int i=0; i<256;i++){
                if(i==36) continue;
                if(n.children[i].used==1){
                    if(i==(int)c){
                        String sd=s.substring(1);
                        rtrn.addAll(someName1(sd,num, (IntNode)n.children[(int)c].node));
                    }
                    else{
                        String sd=s.substring(1);
                        int numd=num-1;
                        rtrn.addAll(someName1(sd,numd,(IntNode)n.children[i].node));
                    }
                }
            }
        }
        /*else{
            char c = s.charAt(0);
            if(n.children[(int) c].used==1) {
                s=s.substring(1);
                rtrn.addAll(someName1(s,num, (IntNode)n.children[(int)c].node));
            }
            else{
                s=s.substring(1);
                num--;
                for(int i=0;i<256;i++){
                    if(i==36) {continue;}
                    if(n.children[i].used==1){
                        rtrn.addAll(someName1(s,num,(IntNode)n.children[i].node));
                       //rtrn.addAll(someName1(s,num,n));
                    }
                }
            }
        }*/
        return rtrn;
    }

    public Vector posSubstringsNotOverlap(String s){
        int flag1=0;
        Vector<Integer> vec;
        for(int o=0;o<256;o++){
           if(this.root.children[o].used==1) flag1=1;
        }
       if(flag1==0) return null;
        int flag=1;
        IntNode nd=root; 
        int i;
        for(i=0;i<s.length();i++){
            char c=s.charAt(i);
            if(nd.children[(int) c].c==c) nd=(IntNode) nd.children[(int)c].node;
            else {flag=0; break;}
        }
        if(i==s.length()){
            vec= getPositions(nd);
            Collections.sort(vec);
            for(int j=0;j<vec.size()-1;j++){
                if(vec.get(j+1)-vec.get(j)<s.length()) vec.remove(j);
            }
            return vec;
        }else return null;
    }

    public void performAction(String actionMessage){
       String[] action=actionMessage.split("\\s+");
		if(action[0].equals("makeSuffixTrie")){
            System.out.print(actionMessage+" : ");
           // System.out.print(action[0]+" "+action[1]+" : ");
            try{
                String content = new Scanner(new File(action[1])).useDelimiter("\\Z").next();
                content=content.toLowerCase();
                makeSuffixTrie(content);
                System.out.println("Added");
            } catch(IOException e){
                System.out.println("Exception : "+action[1]+" file does not exist");
            }
		}
        if(action[0].equals("isSubstring")){
            System.out.print(actionMessage+" : ");
            //System.out.print(action[0]+" "+action[1]+" : ");
            String query=action[1];
            query=query.toLowerCase();
            int r=isSubstring(query);
            if(r==-1) System.out.println("Exception-Trie has not been built yet");
            else
            System.out.println(r);
        }
        if(action[0].equals("numSubstrings")){
            System.out.print(actionMessage+" : ");
            //System.out.print(action[0]+" "+action[1]+" : ");
            String query=action[1];
            query=query.toLowerCase();
            if(action[2].equals("1")){
                int r=numSubstrings(query);
                if(r==-1) System.out.println("Exception-Trie has not been built yet");
                else if(r==0)System.out.println("Exception-This pattern does not exists");
                else
                System.out.println(r);
            }
            if(action[2].equals("0")){
                int r=numSubstringsNotOverlap(query);
                if(r==-1) System.out.println("Exception-Trie has not been built yet");
                else if(r==0)System.out.println("Exception-This pattern does not exists");
                else
                System.out.println(r);
            }
            
        }
        if(action[0].equals("posSubstrings")){
            System.out.print(actionMessage+" : ");
            //System.out.print(action[0]+" "+action[1]+" : ");
            String query=action[1];
            query=query.toLowerCase();
            if(action[2].equals("1")){
                Vector<Integer> pos=posSubstrings(query);
                 if((pos==null)||(pos.size()==0)) System.out.println("Exception-Either the string does not exist or the trie has not been built yet");
                 else {
                    for(int i=0;i<pos.size()-1;i++){
                        System.out.print(pos.get(i)+", ");
                    }
                    System.out.println(pos.get(pos.size()-1));
                 }
            }
            if(action[2].equals("0")){
                Vector<Integer> pos=posSubstringsNotOverlap(query);
                 if((pos==null)||(pos.size()==0)) System.out.println("Exception-Either the string does not exist or the trie has not been built yet");
                 else {
                    for(int i=0;i<pos.size()-1;i++){
                        System.out.print(pos.get(i)+", ");
                    }
                    System.out.println(pos.get(pos.size()-1));
                 }
            }
        }
        if(action[0].equals("numFuzzySubstrings")){
            System.out.print(actionMessage+" : ");
            //System.out.print(action[0]+" "+action[1]+" : ");
            String query=action[1];
            int num=java.lang.Integer.parseInt(action[2]);
            query=query.toLowerCase();
            if(action[3].equals("1")){
                int r=numFuzzySubstrings(query,num);
                if(r==-1) System.out.println("Exception-Trie has not been built yet");
                else if(r==0)System.out.println("Exception-This pattern does not exists");
                else
                System.out.println(r);
            }
            if(action[3].equals("0")){
                int r=numFuzzySubstrings(query,num);
                if(r==-1) System.out.println("Exception-Trie has not been built yet");
                else if(r==0)System.out.println("Exception-This pattern does not exists");
                else{
                    Vector<Integer> posForNum=new Vector<Integer>();
                    posForNum=posFuzzySubstringsNotOverlap(query,num);
                    System.out.println(posForNum.size());
                }
                
            }
        }

       if(action[0].equals("posFuzzySubstrings")){
            System.out.print(actionMessage+" : ");
            //System.out.print(action[0]+" "+action[1]+" : ");
            String query=action[1];
            int num=java.lang.Integer.parseInt(action[2]);
            query=query.toLowerCase();
            if(num>=query.length()) System.out.println("Exception- wrong input");
            else if(action[3].equals("1")){
                Vector<Integer> pos=posFuzzySubstrings(query,num);
                 if((pos==null)||(pos.size()==0)) System.out.println("Exception-Either the string does not exist or the trie has not been built yet");
                 else {
                    for(int i=0;i<pos.size()-1;i++){
                        System.out.print(pos.get(i)+", ");
                    }
                    System.out.println(pos.get(pos.size()-1));
                 }
            }
            else if(action[3].equals("0")){
                Vector<Integer> pos=posFuzzySubstringsNotOverlap(query,num);
                 if((pos==null)||(pos.size()==0)) System.out.println("Exception-Either the string does not exist or the trie has not been built yet");
                 else {
                    for(int i=0;i<pos.size()-1;i++){
                        System.out.print(pos.get(i)+", ");
                    }
                    System.out.println(pos.get(pos.size()-1));
                 }
            }
            else{}
        } 

    }
}