import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.lang.*;
import java.util.*;
public class SearchEngine{
InvertedPageIndex invPageIndex;
	public SearchEngine() {
		invPageIndex=new InvertedPageIndex();
	}
	public String removeSFromEnd(String s){
        if(s.endsWith("s")){
            return s.substring(0,s.length()-1);
        }
        else return s;
    } 

	public void performAction(String actionMessage) {
		String[] action=actionMessage.split("\\s+");
		if(action[0].equals("addPage")){
			System.out.println("adding page "+action[1]);
			PageEntry p=new PageEntry(action[1]);
			invPageIndex.addPage(p);
		}
		if(action[0].equals("queryFindPagesWhichContainWord")){
			String s=action[1].toLowerCase();
			s=removeSFromEnd(s);
			System.out.print("Search Results for queryFindPagesWhichContainWord for "+ action[1]+" : ");
			Myset<PageEntry> pages=invPageIndex.getPagesWhichContainWord(s);
			if(pages!=null){
				LinkedList<PageEntry> list=pages.newll;
				ListIterator<PageEntry> itr=list.listIterator();
				System.out.print(itr.next().getPageName());
				while(itr.hasNext()){
					System.out.print(", "+itr.next().getPageName());
				}
				System.out.println("");
			}
		}
		if(action[0].equals("queryFindPositionsOfWordInAPage")){
			String s=action[1].toLowerCase();
			s=removeSFromEnd(s);
			System.out.print("Search Results for queryFindPositionsOfWordInAPage for "+ action[1]+" in page "+action[2]+" : ");
			Myset<Position> posi=invPageIndex.getPositionOfWordInAPage(s,action[2]);
			if(posi!=null){
				LinkedList<Position> list=posi.newll;
				if(list.isEmpty()){
					System.out.println("\""+action[2]+"\" does not contain the word \""+s+"\" or the page has not been indexed or crawled in the search engine");
				}
				else{
					ListIterator<Position> itr=list.listIterator();
					System.out.print(itr.next().getWordIndex());
					while(itr.hasNext()){
						System.out.print(", "+itr.next().getWordIndex());
					}
					System.out.println("");
				}
			}
		}
	}
}
