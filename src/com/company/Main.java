package com.company;
import java.util.*;
import java.io.*;

public class Main {

    public static void main(String[] args) {
        questionFive();

    }

    public static void questionFive() {
        String fileName = System.getProperty("user.dir") + "\\src\\com\\company\\questionFive.txt";
        ArrayList<Integer> numList = new ArrayList<Integer>();
        String line;

        try {
            FileReader fileReader = new FileReader(fileName);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            int valids = 0;
            while((line = bufferedReader.readLine()) != null) {
                Integer num =
                HashSet<String> wordSet = new HashSet<String>();
                boolean valid = true;
                for (String word : words) {
                    if (wordSet.contains(word)) {
                        valid = false;
                    }
                    wordSet.add(word);
                }
                if (valid) {
                    valids++;
                }
            }
            bufferedReader.close();
            System.out.println(valids);
        } catch(FileNotFoundException ex) {
            System.out.println("Unable to open file '" + fileName + "'");
        } catch(IOException ex) {
            System.out.println("Error reading file '" + fileName + "'");
        }
    }

    public static void questionFour() {
        String fileName = System.getProperty("user.dir") + "\\src\\com\\company\\questionFour.txt";

        String line;

        try {
            FileReader fileReader = new FileReader(fileName);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            int valids = 0;
            while((line = bufferedReader.readLine()) != null) {
                String[] words = line.split(" ");
                HashSet<String> wordSet = new HashSet<String>();
                boolean valid = true;
                for (String word : words) {
                    if (wordSet.contains(word)) {
                        valid = false;
                    }
                    wordSet.add(word);
                }
                if (valid) {
                    valids++;
                }
            }
            bufferedReader.close();
            System.out.println(valids);
        } catch(FileNotFoundException ex) {
            System.out.println("Unable to open file '" + fileName + "'");
        } catch(IOException ex) {
            System.out.println("Error reading file '" + fileName + "'");
        }

        try {
            FileReader fileReader = new FileReader(fileName);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            int valids = 0;
            while((line = bufferedReader.readLine()) != null) {
                String[] words = line.split(" ");
                ArrayList<HashMap<String, Integer>> wordLetterCounts = new ArrayList<HashMap<String, Integer>>();
                boolean valid = true;
                for (String word : words) {
                    HashMap<String, Integer> wordLetterCount = toLetterCount(word);
                    if (containsAnagram(wordLetterCounts,wordLetterCount)) {
                        valid = false;
                        break;
                    }
                    wordLetterCounts.add(wordLetterCount);
                }
                if (valid) {
                    valids++;
                }
            }
            bufferedReader.close();
            System.out.println(valids);
        } catch(FileNotFoundException ex) {
            System.out.println("Unable to open file '" + fileName + "'");
        } catch(IOException ex) {
            System.out.println("Error reading file '" + fileName + "'");
        }
    }

    public static HashMap<String, Integer> toLetterCount(String word) {
        HashMap<String, Integer> letterCounts = new HashMap<String, Integer>();
        for (int index = 0; index < word.length(); index++) {
            String letter = word.substring(index, index+1);
            if (letterCounts.containsKey(letter)) {
                letterCounts.put(letter, letterCounts.get(letter) + 1);
            } else {
                letterCounts.put(letter, 1);
            }
        }
        return letterCounts;
    }

    public static boolean containsAnagram(ArrayList<HashMap<String, Integer>> allWordToLetterCounts, HashMap<String, Integer> wordLetterCount) {
        Set<String> wordKeySet = wordLetterCount.keySet();
        for (int index = 0; index < allWordToLetterCounts.size(); index++) {
            HashMap<String, Integer> wordInPassPhraseLetterCount = allWordToLetterCounts.get(index);
            Set<String> wordInPassPhraseKeySet = wordInPassPhraseLetterCount.keySet();
            boolean isAnagram = true;
            if (wordInPassPhraseKeySet.containsAll(wordKeySet) && wordKeySet.containsAll(wordInPassPhraseKeySet)) {
                for (String key : wordInPassPhraseLetterCount.keySet()) {
                    if (wordInPassPhraseLetterCount.get(key).compareTo(wordLetterCount.get(key)) != 0) {
                        isAnagram = false;
                        break;
                    }
                }
            } else {
                isAnagram = false;
            }
            if (isAnagram) {
                return true;
            }
        }
        return false;
    }

    public static void questionThree() {
        int input = 312051;
        int layers = 0;
        int last = 0;
        int current = 1;
        while (current * current <= input) {
            last = current;
            current += 2;
            layers++;
        }
        System.out.println("We are " + layers + " layers from the access port.");
        System.out.println("The last squared number is " + (last * last) + ", from a previous square that is " + last + " steps wide.");
        int borderLength = current;
        int stepsUntilCorner = borderLength - 1;
        System.out.println("The length of the current border is " + borderLength + ", but there are only " + stepsUntilCorner + " steps until the corner.");
        int stepsToWalk = input - (last * last);
        int cornersTurned = 0;
        System.out.println("There are " + stepsToWalk + " steps to walk.");
        while (stepsToWalk > stepsUntilCorner) {
            System.out.println("Walking " + stepsUntilCorner + " steps to the next corner.");
            stepsToWalk -= borderLength - 1;
            cornersTurned++;
            System.out.println("Completed turning at corner " + cornersTurned);
            System.out.println("We have " + stepsToWalk + " steps left.");
        }
        int middle = (borderLength - 1)/2;
        System.out.println("The middle of a side is " + middle + " steps from a corner.");
        System.out.println("Therefore, after cutting through " + layers + " layers, we must walk " + (middle - stepsToWalk) + " steps from the middle.");
        System.out.println("That gives us a grand total of " + (layers + middle - stepsToWalk) + " steps from the access point to reach our data.");
        int total = layers + middle - stepsToWalk;
        System.out.println(total);

        int[] top = { 2, 4, 5 };
        int[] bottom = { 11, 23, 25 };
        int[] right = { 25, 1, 2 };
        int[] left = { 5, 10, 11 };
        int[] currentTop = { 59, 122, 133, 142, 147 };
        int[] currentBottom = new int[5];
        currentBottom[0] = 362;
        currentBottom[1] = 747;
        currentBottom[2] = 806;
        int[] currentRight = { 0, 26, 54, 57, 59 };
        int[] currentLeft = { 147, 304, 330, 351, 362 };
        borderLength = 5;
        stepsUntilCorner = borderLength - 1;
        int stepCounter = 3;
        cornersTurned = 3;
        int beforeLast = 747;
        int lastValue = 806;
        int currentValue = 0;
        int counter = 23;
        int lastSquare = (borderLength - 1) * (borderLength - 1);
        int nextSquare = borderLength * borderLength;
        while (lastValue <= input) {
            while (cornersTurned < 4 && lastValue <= input) {
                while (stepCounter <= stepsUntilCorner && lastValue <= input) {
                    if (counter == lastSquare) { // square
                        currentValue = lastValue + right[1];
                        currentRight[stepCounter] = currentValue;
                    } else if (counter + 1 == nextSquare) { // before square
                        currentValue = lastValue + right[0] + currentRight[1];
                        currentBottom[stepCounter] = currentValue;
                        currentRight[0] = currentValue;
                    } else if (counter - 1 == lastSquare) { // after square
                        currentValue = lastValue + beforeLast + right[1] + right[2];
                        currentRight[2] = currentValue;
                    } else if (stepCounter == stepsUntilCorner) { // corner
                        currentValue = lastValue;
                        if (cornersTurned == 0) {
                            currentValue += top[0];
                            currentRight[currentRight.length - 1] = currentValue;
                            currentTop[0] = currentValue;
                        } else if (cornersTurned == 1) {
                            currentValue += left[0];
                            currentTop[currentTop.length - 1] = currentValue;
                            currentLeft[0] = currentValue;
                        } else if (cornersTurned == 2) {
                            currentValue += bottom[0];
                            currentLeft[currentLeft.length - 1] = currentValue;
                            currentBottom[0] = currentValue;
                        } else if (cornersTurned == 3) {
                            currentValue += right[0] + currentRight[1];
                            currentBottom[currentBottom.length - 1] = currentValue;
                            currentRight[0] = currentValue;
                        }
                    } else if (stepCounter + 1 == stepsUntilCorner) { // before corner
                        currentValue = lastValue;
                        if (cornersTurned == 0) {
                            currentValue += right[right.length - 1] + right[right.length - 2];
                            currentRight[stepCounter] = currentValue;
                        } else if (cornersTurned == 1) {
                            currentValue += top[top.length - 1] + top[top.length - 2];
                            currentTop[stepCounter] = currentValue;
                        } else if (cornersTurned == 2) {
                            currentValue += left[left.length - 1] + left[left.length - 2];
                            currentLeft[stepCounter] = currentValue;
                        } else if (cornersTurned == 3) {
                            System.out.println(bottom[bottom.length - 1] + " " + bottom[bottom.length - 2] + " " + currentRight[1]);
                            currentValue += bottom[bottom.length - 1] + bottom[bottom.length - 2] + currentRight[1];
                            currentBottom[stepCounter] = currentValue;
                        }
                    } else if (stepCounter - 1 == 0) { // after corner
                        currentValue = lastValue + beforeLast;
                        if (cornersTurned == 0) { // this actually never gets hit because the 0th corner is two after square
                            currentValue += right[1] + right[2];
                            currentRight[stepCounter] = currentValue;
                        } else if (cornersTurned == 1) {
                            currentValue += top[0] + top[1];
                            currentTop[stepCounter] = currentValue;
                        } else if (cornersTurned == 2) {
                            currentValue += left[0] + left[1];
                            currentLeft[stepCounter] = currentValue;
                        } else if (cornersTurned == 3) {
                            currentValue += bottom[0] + bottom[1];
                            currentBottom[stepCounter] = currentValue;
                        }
                    } else { // middle of side
                        currentValue = lastValue;
                        if (cornersTurned == 0) {
                            currentValue += right[stepCounter - 2] + right[stepCounter - 1] + right[stepCounter];
                            currentRight[stepCounter] = currentValue;
                        } else if (cornersTurned == 1) {
                            currentValue += top[stepCounter - 2] + top[stepCounter - 1] + top[stepCounter];
                            currentTop[stepCounter] = currentValue;
                        } else if (cornersTurned == 2) {
                            currentValue += left[stepCounter - 2] + left[stepCounter - 1] + left[stepCounter];
                            currentLeft[stepCounter] = currentValue;
                        } else if (cornersTurned == 3) {
                            currentValue += bottom[stepCounter - 2] + bottom[stepCounter - 1] + bottom[stepCounter];
                            currentBottom[stepCounter] = currentValue;
                        }
                    }
                    beforeLast = lastValue;
                    lastValue = currentValue;
                    stepCounter++;
                    counter++;
                }
                stepCounter = 1;
                cornersTurned++;
            }
            cornersTurned = 0;
            borderLength += 2;
            stepsUntilCorner = borderLength - 1;
            lastSquare = nextSquare;
            nextSquare = borderLength * borderLength;
            top = currentTop;
            bottom = currentBottom;
            right = currentRight;
            left = currentLeft;
            currentRight = new int[borderLength];
            currentTop = new int[borderLength];
            currentLeft = new int[borderLength];
            currentBottom = new int[borderLength];
        }
        System.out.println("First value larger than " + input + " is " + lastValue);
    }

    public static void questionTwo() {
        String fileName = System.getProperty("user.dir") + "\\src\\com\\company\\data.txt";

        String line;

        try {
            FileReader fileReader = new FileReader(fileName);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            int checksum = 0;
            while((line = bufferedReader.readLine()) != null) {
                String[] nums = line.split("\t");
                int min = Integer.MAX_VALUE;
                int max = Integer.MIN_VALUE;
                for (String numString : nums) {
                    int num = Integer.parseInt(numString);
                    if (num <= min) {
                        min = num;
                    }
                    if (num >= max) {
                        max = num;
                    }
                }
                checksum += max - min;
            }
            bufferedReader.close();
            System.out.println(checksum);

            fileReader = new FileReader(fileName);
            bufferedReader = new BufferedReader(fileReader);
            checksum = 0;
            while((line = bufferedReader.readLine()) != null) {
                String[] numStrings = line.split("\t");
                int[] nums = new int[numStrings.length];
                for (int index = 0; index < numStrings.length; index++) {
                    nums[index] = Integer.parseInt(numStrings[index]);
                }
                boolean complete = false;
                for (int index = 0; index < nums.length && !complete; index++) {
                    for (int bindex = 0; bindex < nums.length; bindex++) {
                        if (bindex == index) {
                            continue;
                        }
                        if (nums[index] % nums[bindex] == 0) {
                            checksum += nums[index] / nums[bindex];
                            complete = true;
                        }
                    }
                }
            }
            bufferedReader.close();
            System.out.println(checksum);
        } catch(FileNotFoundException ex) {
            System.out.println("Unable to open file '" + fileName + "'");
        } catch(IOException ex) {
            System.out.println("Error reading file '" + fileName + "'");
        }
    }

    public static void questionOne() {
        String code = "7385764686251444473997915123782972536343732657517834671759462795461213782428342931896181695578996274321317419242359534783957372932953774336338118488967172727651862498838195317654289797558683458511126996217953322817229372373455862177844478443391835484591525235651863464891177927244954925827786799436536592561374269299474738321293575385899438446558569241236278779779983587912431395475244796538888373287186921647426866237756737342731976763959499149996315591584716122199183295277439872911371313924594486766479438544417416529743495114819825984524437367225234184772617942525954961136976875325182725754768372684531972614455134523596338355374444273522115362238734383164778129376628621497662965456761631796178353599629887665939521892447361219479646483978798392716119793282717739524897385958273726776318154977675546287789874265339688753977185129334929715486381875286278528247696464162297691698154712775589541945263574897266575996455547625537947927972497979333932115165151462742216327321116291372396585618664475715321298122335789262942284571328414569375464386446824882551918843185195829547373915482687534432942778312542752798313434628498295216692646713137244198123219531693559848915834623825919191532658735422176965451741869666714874158492556445954852299161868651448123825821775363219246244515946392686275545561989355573946924767442253465342753995764791927951158771231944177692469531494559697911176613943396258141822244578457498361352381518166587583342233816989329544415621127397996723997397219676486966684729653763525768655324443991129862129181215339947555257279592921258246646215764736698583211625887436176149251356452358211458343439374688341116529726972434697324734525114192229641464227986582845477741747787673588848439713619326889624326944553386782821633538775371915973899959295232927996742218926514374168947582441892731462993481877277714436887597223871881149693228928442427611664655772333471893735932419937832937953495929514837663883938416644387342825836673733778119481514427512453357628396666791547531814844176342696362416842993761919369994779897357348334197721735231299249116477";
        int sum = 0;
        String numString = code.substring(code.length() - 1, code.length());
        int value;
        int last = Integer.parseInt(numString);
        int current = 0;
        for (int index = 0; index < code.length(); index++) {
            numString = code.substring(index, index+1);
            current = Integer.parseInt(numString);
            if (current == last) {
                sum += current;
            }
            last = current;
        }
        System.out.println(sum);

        int halfway = code.length() / 2;
        String numStringHalfAway = "";
        int valueHalfway = 0;
        sum = 0;
        for (int index = 0; index < code.length(); index++) {
            numString = code.substring(index, index+1);
            if (index >= halfway) {
                numStringHalfAway = code.substring(index - halfway, index - halfway + 1);
            } else {
                numStringHalfAway = code.substring(index + halfway, index + halfway + 1);
            }
            current = Integer.parseInt(numString);
            valueHalfway = Integer.parseInt(numStringHalfAway);
            if (current == valueHalfway) {
                sum += current;
            }
        }
        System.out.println(sum);
    }
}
