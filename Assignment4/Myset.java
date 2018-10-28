import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.lang.*;
import java.util.*;
public class Myset<T>{
    LinkedList<T> newll=new LinkedList<T>();
    
    public boolean isEmpty(){
        return newll.isEmpty();
    }

    public boolean isObject(T o){
        ListIterator<T> iterator=this.newll.listIterator();
        while(iterator.hasNext()){
            if(iterator.next().equals(o)) return true;
        }
        return false;
    }

    public void addElement(T o){
        newll.add(o);
    }

    public Myset Union(Myset a){
        if(a.isEmpty()) return this;
        Myset unioned=new Myset();
        unioned.newll = (LinkedList) this.newll.clone();
        ListIterator<T> iterator=a.newll.listIterator();
        while(iterator.hasNext()){
           T obj=iterator.next();
            if(!unioned.isObject(obj)){
                unioned.addElement(obj);                
            }
        }
        return unioned;
    }

    public Myset Intersection(Myset a){
        Myset intersected=new Myset();
        ListIterator<T> iterator=a.newll.listIterator();
        while(iterator.hasNext()){
           T obj=iterator.next();
            if(this.isObject(obj)){
                intersected.addElement(obj);                
            }
        }
        return intersected;
        
    }
}
class Link<T>{
	public T data;
	Link nextLink;
    public Link(){
        nextLink=null;
    }
	public Link(T d){
		data=d;
        nextLink=null;
	}
    public T getData(){
        return data;
    }
}

class Position{
    PageEntry p;
    int wordIndex;
    public PageEntry getPageEntry(){
        return p;
    }
    public int getWordIndex(){
        return wordIndex;
    }
}
class  MyLinkedList<T>{
	 Link<T> first=null;
	 Link<T> last=null;
	public synchronized boolean isEmpty(){
		return (first==null);
	}
	public void addInLast(T d){
		Link<T> n=new Link<T>(d);
		if(isEmpty()){
			n.nextLink=first;
			first=n;
			last=n;
		}else{
			last.nextLink=n;
			last=n;
			last.nextLink=null;
		}
	}
	public T removeFromFirst(){
		Link<T> temp=first;
		if(first.nextLink==null) last=null;
		first=first.nextLink;
		return temp.getData();
	}
	public T lookFirst(){
		return last.data;
	}
	
	public void removeThis(Link toBeRemoved){
		Link current=first.nextLink;
		Link prev=first;
		if(((prev.data).equals(toBeRemoved.data))&&(current==null)){ first=null; last=null;}
		else if(((prev.data).equals(toBeRemoved.data))&&(current!=null)){ first=first.nextLink; }
		else{
			while(current!=null){
				if((current.data).equals(toBeRemoved.data)) break;
				else {prev=current; current=current.nextLink;}
			}
		prev.nextLink=current.nextLink;
		}
	}
    public int size(){
        Link itr=first;
        int count=0;
        while(itr!=null){
            count++;
            itr=itr.nextLink;
        }
    return count;
    }
}
class WordEntry{
    String word;
    MyLinkedList<Position> listOfPositions =new MyLinkedList<Position>();
    public WordEntry(String str){
        this.word=str;
    }
    public void addPosition(Position p){
        listOfPositions.addInLast(p);
    }
    public void addPositions(MyLinkedList<Position> positions){
        if(listOfPositions.isEmpty()){
            if(!positions.isEmpty()){
                listOfPositions.first=positions.first;
                listOfPositions.last=positions.last;
            }
        }else{
            if(!positions.isEmpty()){
                listOfPositions.last.nextLink=positions.first;
                listOfPositions.last=positions.last;
            }
        }
    }
    public MyLinkedList<Position> getAllPositionsForThisWord(){
        return listOfPositions;
    }
}
class PageIndex{
    MyLinkedList<WordEntry> wordEntryList=new MyLinkedList<WordEntry>();

    public void addPositionForWord(String str, Position p){
        Link<WordEntry> itr=wordEntryList.first;
        if(itr==null){
            WordEntry we=new WordEntry(str);
            we.addPosition(p);
            wordEntryList.addInLast(we);
        }
        else{
            while(itr.nextLink!=null){
                if(itr.data.word.equals(str)) break;
                itr=itr.nextLink;
            }
            WordEntry obj=itr.data;
            if(obj.word.equals(str)) obj.addPosition(p);
            else{
                WordEntry we=new WordEntry(str);
                we.addPosition(p);
                wordEntryList.addInLast(we);
            }
        }
    }
    public MyLinkedList<WordEntry> getWordEntries(){
        return wordEntryList;
    }
}
class PageEntry{
    PageIndex pageIndex;
    String pageName;
    public PageEntry(String pn){
        pageName=pn;
        pageIndex=new PageIndex();
        try {
            BufferedReader in = new BufferedReader(new FileReader("./webpages/"+pageName));
            String strLine;
            int count=0;
            while ((strLine = in.readLine()) != null) {
                for(int i=0;i<strLine.length();i++){                   
                    if((strLine.charAt(i)=='{')||(strLine.charAt(i)=='}')||(strLine.charAt(i)=='[')||(strLine.charAt(i)==']')||(strLine.charAt(i)=='<')||(strLine.charAt(i)=='>')||(strLine.charAt(i)=='(')||(strLine.charAt(i)==')')||(strLine.charAt(i)=='=')||(strLine.charAt(i)=='.')||(strLine.charAt(i)==',')||(strLine.charAt(i)==';')||(strLine.charAt(i)=='"')||((int )strLine.charAt(i)==39)||(strLine.charAt(i)=='?')||(strLine.charAt(i)=='#')||(strLine.charAt(i)=='!')||(strLine.charAt(i)=='-')||(strLine.charAt(i)==':')){
                     strLine= strLine.substring(0,i) + " " + strLine.substring(i+1);
                    }
                }
                String[] str=strLine.split("\\s+");
                for (String s: str){
                    s=s.toLowerCase();
                    s=removeSFromEnd(s);
                    if((s.equals("a"))||(s.equals("an"))||(s.equals("the"))||(s.equals("they"))||(s.equals("these"))||(s.equals("this"))||(s.equals("for"))||(s.equals("is"))||(s.equals("are"))||(s.equals("was"))||(s.equals("of"))||(s.equals("or"))||(s.equals("and"))||(s.equals("does"))||(s.equals("will"))||(s.equals("whose"))){
                        count++;
                    }
                    else{
                        count++;
                        Position posi=new Position();
                        posi.p=this;
                        posi.wordIndex=count;
                        pageIndex.addPositionForWord(s,posi);
                    }
                }
            }
            in.close();
        }catch (IOException e) {
            System.out.println(e);
        }
    }
    boolean equals(PageEntry pe){
        return (pe.getPageName().equals(this.pageName));
    }
    public String removeSFromEnd(String s){
        if(s.endsWith("s")){
            return s.substring(0,s.length()-1);
        }
        else return s;
    } 
    public PageIndex getPageIndex(){
        return pageIndex;
    }
    public String getPageName(){
        return pageName;
    }
}

class MyHashTable{
    Link<WordEntry>[] hashArray= new Link[50];
    public MyHashTable(){
        for(int i=0; i<50;i++){
        Link<WordEntry> timepass=new Link<WordEntry>();
        hashArray[i]=timepass;
        timepass.nextLink=null;
        timepass.data=null;
        }
    }
    private int getHashIndex(String str){
        int hashIndex=0;
        for(int i=0; i<str.length();i++){
            hashIndex+=(i+1)*((int)str.charAt(i));
        }
        return (hashIndex%50);
    }
    public void addPositionsForWord(WordEntry w){
        int index=getHashIndex(w.word);
        if(hashArray[index].nextLink==null){
            Link<WordEntry> we=new Link<WordEntry>(w);
            we.nextLink=null;
            hashArray[index].nextLink=we;
        }else{
            Link<WordEntry> itr=hashArray[index].nextLink;
            while(itr.nextLink!=null){
                if(itr.data.word.equals(w.word)) break;
                itr=itr.nextLink;
            }
            WordEntry obj=itr.data;
            if(obj.word.equals(w.word)) obj.addPositions(w.listOfPositions);
            else{
                WordEntry we=new WordEntry(w.word);
                we.addPositions(w.listOfPositions);
                Link<WordEntry> lwe=new Link<WordEntry>();
                lwe.nextLink=null;
                lwe.data=we;
                itr.nextLink=lwe;
            }    
        }
    }
    public WordEntry getWordEntry(String str){
        int index=getHashIndex(str);
        Link<WordEntry> itr=hashArray[index].nextLink;
        while(itr!=null){
            if(itr.data.word.equals(str)) break;
            itr=itr.nextLink;
        }
        if(itr!=null)
        return itr.data;
        else return null;
    }
}
class InvertedPageIndex{
    MyHashTable hashTable=new MyHashTable();
    public void addPage(PageEntry p){
        MyLinkedList<WordEntry> wordsInPage=p.getPageIndex().getWordEntries();
        Link<WordEntry> itr=wordsInPage.first;
        for(int i=0; i<wordsInPage.size(); i++){
            hashTable.addPositionsForWord(itr.data);
            itr=itr.nextLink;
        }
    }
    Myset<PageEntry> getPagesWhichContainWord(String str){
        try{
            WordEntry wordEntryOfThisWord=hashTable.getWordEntry(str);
            if(wordEntryOfThisWord==null) throw new Exception(); 
            Myset<PageEntry> setOfPagesContainingThisWord=new Myset<PageEntry>();
            MyLinkedList<Position> posiList= wordEntryOfThisWord.getAllPositionsForThisWord();
            Link<Position> itr=posiList.first;
            for(int i=0; i<posiList.size();i++){
                if(!setOfPagesContainingThisWord.isObject(itr.data.getPageEntry())){
                    setOfPagesContainingThisWord.addElement(itr.data.getPageEntry());
                }
                itr=itr.nextLink;
            }
            return setOfPagesContainingThisWord;
        }catch(Exception e){
            System.out.println("No result found for query \""+str+ "\"");
        }
        return null;
    }
    Myset<Position> getPositionOfWordInAPage(String word, String pageName){
        try{
          WordEntry wordEntryOfThisWord=hashTable.getWordEntry(word);
          if(wordEntryOfThisWord==null) throw new Exception(); 
          Myset<Position> setOfPositionOfTheWordInPage=new Myset<Position>(); 
          MyLinkedList<Position> posiList= wordEntryOfThisWord.getAllPositionsForThisWord();
          Link<Position> itr=posiList.first;
            for(int i=0; i<posiList.size();i++){
                if(itr.data.getPageEntry().getPageName().equals(pageName)){
                    setOfPositionOfTheWordInPage.addElement(itr.data);
                }
                itr=itr.nextLink;
            }
            return setOfPositionOfTheWordInPage;
        }catch(Exception e){
            System.out.println("\""+pageName+"\" does not contain the word \""+word+"\" or the page has not been indexed or crawled in the search engine");
        }
        return null;
    }
}