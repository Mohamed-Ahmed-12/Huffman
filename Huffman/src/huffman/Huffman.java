package huffman;
import java.io.File;
import java.io.FileNotFoundException; 
import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Scanner;
import java.util.Comparator;

class Node{
    int frequency;
    char symbol;
    Node left;
    Node right;
}
 class CompareFreq implements Comparator<Node>{    
    @Override
    public int compare(Node n1,Node n2){//to compare between to Huffman nodes n1,n2
        return n1.frequency - n2.frequency;
    }
 }
public class Huffman {
  private static Scanner input;
    public static void printHuffmanCode(Node root, String H){
        if (root.left == null && root.right == null && Character.isLetter(root.symbol))
        {
            System.out.println(root.symbol + " --> "+ H);
            return;
        }
        //left  add "0" to the code or right add"1" to the code
        // recursive calls for left and right sub-tree of the generated tree.
        printHuffmanCode(root.left, H + "0");
        printHuffmanCode(root.right, H + "1");    
    }
       public static void Decompress(String a, String arr[], String arr2[]) {
        String s = "";
        int l;
        for (int i = 0; i < a.length(); i++) {
            for (int j = i; j <= a.length(); j++) {
                for (int k = 0; k < arr2.length; k++) {
                    if (a.substring(i, j).equals(arr2[k])) {
                        s = s + arr[k];
                        l = arr2[k].length();
                        i = i + l;
                        break;
                    }
                }
            }
        }
        System.out.println(s);
    }
    public static void main(String[] args) throws FileNotFoundException {   
        System.out.println("***Standard Huffman Code***");
        System.out.println("Choose 1-Compress 2-Decompress");
        Scanner in = new Scanner(System.in);
        int choice=in.nextInt();
        
        if(choice==1){
            //Read characters from file
            File myObj = new File("file.txt");
            String str = "";
            if(myObj.exists())
            {
                Scanner input = new Scanner(myObj); 
                while (input.hasNextLine()) {
                    str = input.nextLine();
                } 
                System.out.println("Characters in file: " + str);
                input.close();
            }
            int Size= str.length();
            System.out.println("Length of characters: " + Size);

            //Displays the each character and their corresponding frequency  
            int[] freq = new int[Size];  
            int i, j;
            //Converts given string into character array  
            char string[] = str.toCharArray();   
            for(i = 0; i <str.length(); i++) {  
                freq[i] = 1;  
                for(j = i+1; j <str.length(); j++) {  
                    if(string[i] == string[j]) {  
                        freq[i]++;  
                        //Set string[j] to 0 to avoid printing visited character  
                        string[j] = '0';  
                    }  
                }  
            }

            System.out.println("Characters and their corresponding frequencies:");
            //Displays the each character and their corresponding frequency  
            for(i = 0; i <freq.length; i++) {  
                if(string[i] != '0')  
                    System.out.println(string[i] +" --> "+ freq[i]);  
            }

            List<Character> ArrayOfChar = new ArrayList<>();
            for(i = 0;i<str.length();i++)
            {

                if(!(ArrayOfChar.contains(str.charAt(i))))
                {
                    ArrayOfChar.add(str.charAt(i));
                }
            }
            int size = ArrayOfChar.size();
            int[] charFreq = new int[size];
            int len=0;
            for(j=0;j<size;j++){
                for(int k=0;k<str.length();k++){
                    if(ArrayOfChar.get(j)==str.charAt(k)){
                        len++;
                    }
                }
                charFreq[j] = len;
                len=0;
            }

            PriorityQueue<Node> pq = new PriorityQueue<>(size, new CompareFreq());
            for (i = 0; i < size; i++) 
            {
                // creating a Huffman node object and add it to the priority queue.
                Node node = new Node();
                node.symbol = ArrayOfChar.get(i);
                node.frequency = charFreq[i];
                node.left = null;
                node.right = null;
                pq.add(node);
            }
            // create a root node
            Node root = null;
            while (pq.size() > 1) 
            {
                Node left = pq.peek();
                pq.poll();
                Node right = pq.peek();
                pq.poll();
                Node parent = new Node();
                parent.frequency = left.frequency + right.frequency;
                parent.symbol = '-';
                parent.left = left;
                parent.right = right;
                root = parent;
                pq.add(parent);
            }
            System.out.println("Characters and corresponding huffman code: ");
            printHuffmanCode(root, "");
            
    }else if(choice==2){        
            System.out.println("enter the string that will be decompressed ");
            in = new Scanner(System.in);
            int counter = 0;

            String a = in.nextLine();
            for (int k = 0; k < a.length(); k++) {

                if (a.charAt(k) != '0' && a.charAt(k) != '1' && a.charAt(k) != ',' && a.charAt(k) != ' ') {
                    counter++;
                }
            }
            String[] a1 = new String[counter];
            String[] a2 = new String[counter];
            for (int i = 0; i < counter; i++) {
                a2[i] = "";
            }
            counter = 0;
            for (int k = 0; k < a.length(); k++) {
                if (a.charAt(k) != '0' && a.charAt(k) != '1' && a.charAt(k) != ',' && a.charAt(k) != ' ') {
                    a1[counter] = String.valueOf(a.charAt(k));
                    k++;
                    for (;a.charAt(k) != ',' && k < a.length()-1;k++) {

                        a2[counter] += String.valueOf(a.charAt(k));                    
                    }
                }
                counter++;
                //e011,d010,c11,a10,b00
            }
            a2[counter-1] += String.valueOf(a.charAt(a.length()-1));
            String v = in.nextLine();
            Decompress(v, a1, a2);
    }else{
            System.out.println("Error");
    }   
    }
}
//BCCADBBDABBCCADDCCEE