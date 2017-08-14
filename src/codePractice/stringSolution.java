package codePractice;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class stringSolution {
    public int strStr(String haystack, String needle) {
        if (needle.length() == 0) return 0;
        int sol = -1;
        int lenh = haystack.length(), lenn = needle.length();
        if (lenh < lenn) return -1;
        int i = 0, j = 0, start;
        
        int [] kmp = ParseKMP(needle);
        System.out.print(kmp.toString());
        while (i < lenh && j < lenn) {
            while (i < lenh && j < lenn && haystack.charAt(i) == needle.charAt(j)) {i++; j++;}
            if (j == lenn) return i - j;
            if (i == lenh) return sol;
            
            if (j > 0) j = kmp[j - 1];
            else i++;
            
        }
        return sol;
    }

    public int[] ParseKMP (String needle) {
        int[] kmp = new int[needle.length()];
        int lenN = needle.length();
        kmp[0] = 0;
        for (int i = 1, len = 0; i < lenN;) { // len for prefix matching, i for iterating
            if (needle.charAt(i) == needle.charAt(len)) kmp[i++] = ++len;
            else if(len > 0) len = kmp[len - 1];
            else kmp[i++] = 0;
        }
        return kmp;
    }
    
    /**
    * Store the initial position of elements in the hashmap< index,character>
    * during permutation you only swap i with j
    * if element at index j element[j] != hashMap.get(i)
    */
   public List<char[]> getDerragement(char[] chars){
       List<char[]> result = new ArrayList<char[]>();
       if(chars == null || chars.length == 0){
           return result;
       }
       Map<Integer,Character> indexCharacterMap = new HashMap<Integer,Character>();
       int i = 0;
       for(char c:chars){
           indexCharacterMap.put(i,c);
           i++;
       }
       permute(chars,indexCharacterMap,0,result);
       return result;
   }

   private void permute(char[] chars,Map<Integer,Character> indexCharacterMap,int start,List<char[]> result){
       if(start>=chars.length){
           result.add(Arrays.copyOf(chars,chars.length));
           return;
       }

       for(int i = start;i<chars.length;i++){
           if(indexCharacterMap.get(i)!=chars[start]){
               swap(chars,i,start);
               permute(chars,indexCharacterMap,start+1,result);
               swap(chars,i,start);
           }
       }
   }

   private void swap(char[] chars,int i,int j){
       char temp = chars[i];
       chars[i] = chars[j];
       chars[j] = temp;
   }
   
   public List<char[]> getDerrange(char[] chars){
	   boolean[] taken = new boolean[chars.length];
	   char[] carry = new char[chars.length];
	   List<char[]> sol = new ArrayList<>();
	   permute(chars, carry, taken, sol, 0);
	   return sol;
   }
   
   public void permute(char[] chars, char[] carry, boolean[] taken, List<char[]> sol, int digit) {
	   if(digit == chars.length) {
		   sol.add(carry.clone());
		   return;
	   }
	   for(int i = 0; i < chars.length; i++) {
		   if(chars[i] == chars[digit] || taken[i]) continue;
		   carry[digit] = chars[i];
		   taken[i] = true;
		   permute(chars, carry, taken, sol, digit + 1);
		   taken[i] = false;
	   }
   }

//   public static void main(String argsp[]){
//       Dearrangements dearrangements = new Dearrangements();
//       char[] chars = {'A','B','C','D','E'};
//       List<char[]> result = dearrangements.getDerragement(chars);
//       for(char[] items : result){
//           for(char item:items){
//               System.out.print(" "+item);
//           }
//           System.out.println();
//       }
//   }
}
