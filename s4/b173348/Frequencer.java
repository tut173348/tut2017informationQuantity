package s4.b173348; // Please modify to s4.Bnnnnnn, where nnnnnn is your student ID.
import java.lang.*;
import s4.specification.*;

public class Frequencer implements FrequencerInterface{
  // Code to start with: This code is not working, but good start point to work.
  byte [] myTarget;
  byte [] mySpace;
  boolean targetReady = false;
  boolean spaceReady = false;
  int [] suffixArray;
  // The variable, "suffixArray" is the sorted array of all suffixes of mySpace.
  // Each suffix is expressed by a interger, which is the starting position in mySpace.
  // The following is the code to print the variable
  private void printSuffixArray() {
    if(spaceReady) {
      for(int i=0; i< mySpace.length; i++) {
        int s = suffixArray[i];
        for(int j=s;j<mySpace.length;j++) {
          System.out.write(mySpace[j]);
        }
        System.out.write('\n');
      }
    }
  }

  private int suffixCompare(int i, int j) {
    /*
    if(mySpace[i] > mySpace[j]) { return 1;}
    if(mySpace[i] < mySpace[j]) { return -1;}
    if(mySpace[i] == mySpace[j]) { return 0;}
    */
    // comparing two suffixes by dictionary order.
    // i and j denoetes suffix_i, and suffix_j
    // if suffix_i > suffix_j, it returns 1
    // if suffix_i < suffix_j, it returns -1
    // if suffix_i = suffix_j, it returns 0;
    // It is not implemented yet,
    // It should be used to create suffix array.
    // Example of dictionary order
    // "i" < "o" : compare by code
    // "Hi" < "Ho" ; if head is same, compare the next element
    // "Ho" < "Ho " ; if the prefix is identical, longer string is big
    //System.out.println("i = " + i);


    int si = suffixArray[i];
    int sj = suffixArray[j];
    int s = 0;
    if(si > s) s = si;
    if(sj > s) s = sj;
    int n = mySpace.length - s;
    for(int k = 0; k < n; k++) {
      if(mySpace[si + k] > mySpace[sj + k]) {return 1;}
      if(mySpace[si + k] < mySpace[sj + k]) {return -1;}
    }
    if(si < sj) return 1;
    if(si > sj) return -1;

    // int b = j;
    // if(i > j) {
    //   b = i;
    // }
    // for(int idx = 0; idx < mySpace.length - b; idx++) {
    //   if(mySpace[i + idx] > mySpace[j + idx]) {
    //     return 1;
    //   }
    //   if(mySpace[i + idx] < mySpace[j + idx]) {
    //     return -1;
    //   }
    // }
    // if(i < j) return 1;
    // if(i > j) return -1;

    return 0;
  }

  public void setSpace(byte []space) {
    mySpace = space; if(mySpace.length>0) spaceReady = true;
    suffixArray = new int[space.length];
    // put all suffixes in suffixArray. Each suffix is expressed by one interger.
    for(int i = 0; i< space.length; i++) {
      suffixArray[i] = i;
    }
    // Sorting is not implmented yet.
    /* Example from "Hi Ho Hi Ho"
    0: Hi Ho
    1: Ho
    2: Ho Hi Ho
    3:Hi Ho
    4:Hi Ho Hi Ho
    5:Ho
    6:Ho Hi Ho
    7:i Ho
    8:i Ho Hi Ho
    9:o
    A:o Hi Ho
    */
    //前回のSuffixArrayソート
    // for(int i = 0; i < mySpace.length - 1; i++) {
    //   for(int j = i + 1; j < mySpace.length; j++) {
    //     int compare = suffixCompare(i, j);
    //     if(compare == 1) {
    //       int tmp = suffixArray[i];
    //       suffixArray[i] = suffixArray[j];
    //       suffixArray[j] = tmp;
    //     }
    //   }
    // }
    quick_sort(suffixArray, 0, suffixArray.length - 1);
    printSuffixArray();
  }

  public void quick_sort(int[] sortArray, int suffixLeft, int suffixRight) {
    if(suffixLeft >= suffixRight) {
      return;
    }
    int pivot = (suffixLeft + suffixRight) / 2 + (suffixLeft + suffixRight) % 2;
    int sortL = suffixLeft;
    int sortR = suffixRight;
    int tmp;

    //System.out.println("sortArray[" + sortL + "] = " + sortArray[sortL]);
    while(true) {
      while(suffixCompare(sortL, pivot) == -1) { sortL++; }
      while(suffixCompare(sortR, pivot) == 1) { sortR--; }

      if(sortL > sortR) { break; }
      tmp = sortArray[sortL];
      sortArray[sortL] = sortArray[sortR];
      sortArray[sortR] = tmp;
      sortL++;
      sortR--;
    }
    quick_sort(sortArray, suffixLeft, sortR);
    quick_sort(sortArray, sortL, suffixRight);
  }

  private int targetCompare(int i, int start, int end) {
    //start is specified in subByteStartIndex, and subByteEndIndex
    //target_start_end is subByte(start, end) of target
    // comparing suffix_i and target_j_end by dictonary order with limitation of length;
    // if the beginning of suffix_i matches target_i_end, and suffix is longer than target it returns 0;
    // if suffix_i > target_start_end it return 1;
    // if suffix_i < target_start_end it return -1
    // It is not implemented yet.
    // It should be used to search the apropriate index of some suffix.
    // Example of search
    // suffix target
    // "o" > "i"
    // "o" < "z"
    // "o" = "o"
    // "o" < "oo"
    // "Ho" > "Hi"
    // "Ho" < "Hz"
    // "Ho" = "Ho"
    // "Ho" < "Ho " : "Ho " is not in the head of suffix "Ho"
    // "Ho" = "H" : "H" is in the head of suffix "Ho"

    Byte[] characters = new Byte[suffixArray.length];
    int c = 0;
    int s = suffixArray[i];
    int compareResult = 0;
    for(; c < myTarget.length; c++) {
      if(s == mySpace.length) return -1;
      characters[c] = mySpace[s];
      compareResult = characters[c].compareTo(myTarget[c]);
      if(compareResult != 0) {
        if (compareResult < 0 ) return -1;
        if (compareResult > 0 ) return 1;
      }
      s++;
    }

    return 0;
  }

  //binary search
  public int binary_search(int binaryStart, int binaryEnd, int targetStart, int targetEnd, int targetNum) {
    int middle = (binaryStart + binaryEnd) / 2 + (binaryStart + binaryEnd) % 2;
    if(middle == 0) {
      if(targetCompare(middle, binaryStart, binaryEnd) == targetNum) {
        return middle;
      }
      return middle + targetNum;
    }

    if(binaryStart >= binaryEnd) {
      if(middle == suffixArray.length - 1 && targetCompare(middle - 1, targetStart, targetEnd) == targetNum - 1) {
        return middle + targetNum;
      }
      return middle;
    }

    if(targetCompare(middle - 1, targetStart, targetEnd) >= targetNum && targetCompare(middle, targetStart, targetEnd) >= targetNum) {
      return binary_search(binaryStart, middle - 1, targetStart, targetEnd, targetNum);
    }else if(targetCompare(middle - 1, targetStart, targetEnd) < targetNum && targetCompare(middle, targetStart, targetEnd) < targetNum) {
      return binary_search(middle + 1, binaryEnd, targetStart, targetEnd, targetNum);
    }
    return middle;
  }

  private int subByteStartIndex(int start, int end) {
    // It returns the index of the first suffix which is equal or greater than subBytes;
    // not implemented yet;
    // For "Ho", it will return 5 for "Hi Ho Hi Ho".
    // For "Ho ", it will return 6 for "Hi Ho Hi Ho".

    return binary_search(0, suffixArray.length - 1, start, end, 0);
  }

  private int subByteEndIndex(int start, int end) {
    // It returns the next index of the first suffix which is greater than subBytes;
    // not implemented yet
    // For "Ho", it will return 7 for "Hi Ho Hi Ho".
    // For "Ho ", it will return 7 for "Hi Ho Hi Ho".
    return binary_search(0, suffixArray.length - 1, start, end, 1);
  }
  public int subByteFrequency(int start, int end) {
    // This method could be defined as follows though it is slow.
    int spaceLength = mySpace.length;
    int count = 0;
    for(int offset = 0; offset< spaceLength - (end - start); offset++) {
      boolean abort = false;
      for(int i = 0; i< (end - start); i++) {
        if(myTarget[start+i] != mySpace[offset+i]) { abort = true; break; }
      }
      if(abort == false) { count++; }
    }
    System.out.println("SPACE LENGTH = " + spaceLength);
    int first = subByteStartIndex(start,end);
    int last1 = 0;
    if(first == suffixArray.length){
      last1 = suffixArray.length;
    }
    else{
      last1 = subByteEndIndex(start, end);
    }
    /* inspection code
    for(int k=start;k<end;k++) { System.out.write(myTarget[k]); }
    System.out.printf(": first=%d last1=%d\n", first, last1);
    */
    return last1 - first;
  }
  public void setTarget(byte [] target) {
    myTarget = target; if(myTarget.length>0) targetReady = true;
  }
  public int frequency() {
    if(targetReady == false) return -1;
    if(spaceReady == false) return 0;
    return subByteFrequency(0, myTarget.length);
  }
  public static void main(String[] args) {
    Frequencer frequencerObject;
    try {
      long timeStart = System.currentTimeMillis();
      frequencerObject = new Frequencer();
      frequencerObject.setSpace("Hi Ho Hi Ho".getBytes());
      frequencerObject.setTarget("H".getBytes());
      int result = frequencerObject.frequency();
      System.out.print("\nFreq = "+ result+" ");
      if(4 == result) { System.out.println("OK"); }
      else {System.out.println("WRONG"); }
      long timeEnd = System.currentTimeMillis();
      System.out.println((timeEnd - timeStart) + "ms");
    }
    catch(Exception e) {
      System.out.println("STOP");
    }
  }
}
