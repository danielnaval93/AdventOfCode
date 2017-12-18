package advent.code;
import java.util.*;
import java.util.regex.*;
import java.io.*;



public class Main {

    public class Disc {
        String name;
        int weight;
        ArrayList<Disc> children;
        public Disc(String _name, int _weight) {
            name = _name;
            weight = _weight;
            children = new ArrayList<Disc>();
        }
    }

    public static void main(String[] args) {
        Main m = new Main();
        m.questionSeven();

    }

    public void questionSeven() {
        String fileName = System.getProperty("user.dir") + "\\src\\advent\\code\\questionSeven.txt";
        String pattern = "([a-z]+) \\(([0-9]+)\\)( -> ([a-z]+)(, ([a-z]+))*)?";
        Pattern p = Pattern.compile(pattern);
        HashSet<String> discs = new HashSet<String>();
        HashSet<String> allChildren = new HashSet<String>();
        HashSet<Disc> allDiscs = new HashSet<Disc>();
        HashMap<String, Disc> discMap = new HashMap<String, Disc>();
        String line;

        try {
            FileReader fileReader = new FileReader(fileName);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            while((line = bufferedReader.readLine()) != null) {
                Matcher m = p.matcher(line);
                if ( m.matches() ) {
                    String discName = m.group(1);
                    int discWeight = Integer.parseInt(m.group(2));
                    if (m.group(3) != null) {
                        String childrenString = m.group(3);
                        // remove the -> part
                        childrenString = childrenString.substring(4);
                        String[] children = childrenString.split(", ");
                        for (int index = 0; index < children.length; index++) {
                            allChildren.add(children[index]);
                        }
                    }
                    if (discMap.containsKey(discName)) {
                        discMap.get(discName).weight = discWeight;
                    } else {
                        discMap.put(discName, new Disc(discName, discWeight));
                    }
                    discs.add(discName);
                }
            }
            bufferedReader.close();

            fileReader = new FileReader(fileName);
            bufferedReader = new BufferedReader(fileReader);
            while((line = bufferedReader.readLine()) != null) {
                Matcher m = p.matcher(line);
                if ( m.matches() ) {
                    if (m.group(3) != null ) {
                        String discName = m.group(1);
                        String childrenString = m.group(3);
                        // remove the -> part
                        childrenString = childrenString.substring(4);
                        String[] children = childrenString.split(", ");
                        ArrayList<Disc> childDiscs = new ArrayList<Disc>();
                        for (int index = 0; index < children.length; index++) {
                            childDiscs.add(discMap.get(children[index]));
                        }
                        discMap.get(discName).children = childDiscs;
                    }
                }
            }
            bufferedReader.close();
        } catch(FileNotFoundException ex) {
            System.out.println("Unable to open file '" + fileName + "'");
        } catch(IOException ex) {
            System.out.println("Error reading file '" + fileName + "'");
        }
        Disc root = new Disc("",0);
        for (String disc : discs) {
            if (!allChildren.contains(disc)) {
                System.out.println("Root is named " + disc);
                root = discMap.get(disc);
            }
        }
        solve(root);
    }

    public int solve(Disc disc) {
        if (disc.children.isEmpty()) {
            return disc.weight;
        }
        HashMap<Integer, Integer> weights = new HashMap<Integer, Integer>();
        ArrayList<Integer> weightList = new ArrayList<Integer>();
        for (int index = 0; index < disc.children.size(); index++) {
            int weight = solve(disc.children.get(index));
            if (weights.containsKey(weight)) {
                weights.put(weight, weights.get(weight) + 1);
            } else {
                weights.put(weight, 1);
            }
            weightList.add(weight);
        }
        if (weights.keySet().size() > 1) {
            int wrongWeight = -1;
            int rightWeight = -1;
            for (Integer key : weights.keySet()) {
                if (weights.get(key) == 1) {
                    wrongWeight = key;
                } else {
                    rightWeight = key;
                }
            }
            for (int index = 0; index < weightList.size(); index++) {
                if (weightList.get(index) == wrongWeight) {
                    System.out.println("Mismatch weight is on child named " + disc.children.get(index).name + " It is " + wrongWeight + " It should be " + rightWeight);
                }
            }
        }
        int totalWeight = disc.weight;
        for (Integer key : weights.keySet()) {
            totalWeight += key * weights.get(key);
        }
        return totalWeight;
    }

    public void questionSix() {
        String fileName = System.getProperty("user.dir") + "\\src\\advent\\code\\questionSix.txt";
        int[] buckets = new int[0];
        int numBuckets;
        String line;

        try {
            FileReader fileReader = new FileReader(fileName);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            while((line = bufferedReader.readLine()) != null) {
                String[] words = line.split("\t");
                buckets = new int[words.length];
                for (int index = 0; index < words.length; index++) {
                    buckets[index] = Integer.parseInt(words[index]);
                }
            }
            bufferedReader.close();
        } catch(FileNotFoundException ex) {
            System.out.println("Unable to open file '" + fileName + "'");
        } catch(IOException ex) {
            System.out.println("Error reading file '" + fileName + "'");
        }

        numBuckets = buckets.length;
        int counter = 0;
        int maxVolume;
        int maxBucket;
        HashSet<String> snapshots = new HashSet<String>();
        String snapshot = "";
        for (int index = 0; index < numBuckets; index++) {
            snapshot += buckets[index] + ",";
        }
        while (!snapshots.contains(snapshot)) {
            snapshots.add(snapshot);
            snapshot = "";
            counter++;

            // find the highest value and its owning bucket
            maxVolume = Integer.MIN_VALUE;
            maxBucket = -1;
            for (int index = 0; index < numBuckets; index++) {
                int current = buckets[index];
                if (current > maxVolume) {
                    maxVolume = current;
                    maxBucket = index;
                }
            }

            // distribute the buckets contents to all buckets
            int currentBucket = maxBucket;
            buckets[maxBucket] = 0;
            while (maxVolume > 0) {
                currentBucket++;
                if (currentBucket >= numBuckets) {
                    currentBucket = 0;
                }
                buckets[currentBucket]++;
                maxVolume--;
            }

            // take a snapshot
            for (int index = 0; index < numBuckets; index++) {
                snapshot += buckets[index] + ",";
            }
        }
        System.out.println(counter);

        counter = 0;
        snapshots = new HashSet<String>();
        snapshot = "";
        for (int index = 0; index < numBuckets; index++) {
            snapshot += buckets[index] + ",";
        }
        while (!snapshots.contains(snapshot)) {
            snapshots.add(snapshot);
            snapshot = "";
            counter++;

            // find the highest value and its owning bucket
            maxVolume = Integer.MIN_VALUE;
            maxBucket = -1;
            for (int index = 0; index < numBuckets; index++) {
                int current = buckets[index];
                if (current > maxVolume) {
                    maxVolume = current;
                    maxBucket = index;
                }
            }

            // distribute the buckets contents to all buckets
            int currentBucket = maxBucket;
            buckets[maxBucket] = 0;
            while (maxVolume > 0) {
                currentBucket++;
                if (currentBucket >= numBuckets) {
                    currentBucket = 0;
                }
                buckets[currentBucket]++;
                maxVolume--;
            }

            // take a snapshot
            for (int index = 0; index < numBuckets; index++) {
                snapshot += buckets[index] + ",";
            }
        }
        System.out.println(counter);
    }

    public void questionFive() {
        String fileName = System.getProperty("user.dir") + "\\src\\advent\\code\\questionFive.txt";
        ArrayList<Integer> numList = new ArrayList<Integer>();
        String line;

        try {
            FileReader fileReader = new FileReader(fileName);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            while((line = bufferedReader.readLine()) != null) {
                Integer num = Integer.parseInt(line);
                numList.add(num);
            }
            bufferedReader.close();
        } catch(FileNotFoundException ex) {
            System.out.println("Unable to open file '" + fileName + "'");
        } catch(IOException ex) {
            System.out.println("Error reading file '" + fileName + "'");
        }
        int position = 0;
        int jump = 0;
        int next = 0;
        int[] nums = new int[numList.size()];
        for (int index = 0; index < nums.length; index++) {
            nums[index] = numList.get(index).intValue();
        }
        while (position < nums.length) {
            next = nums[position];
            nums[position]++;
            position += next;
            jump++;
        }
        System.out.println(jump);

        position = 0;
        jump = 0;
        next = 0;
        nums = new int[numList.size()];
        for (int index = 0; index < nums.length; index++) {
            nums[index] = numList.get(index).intValue();
        }
        while (position < nums.length) {
            next = nums[position];
            if (nums[position] >= 3) {
                nums[position]--;
            } else {
                nums[position]++;
            }
            position += next;
            jump++;
        }
        System.out.println(jump);
    }

    public void questionFour() {
        String fileName = System.getProperty("user.dir") + "\\src\\advent\\code\\questionFour.txt";

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

    public HashMap<String, Integer> toLetterCount(String word) {
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

    public boolean containsAnagram(ArrayList<HashMap<String, Integer>> allWordToLetterCounts, HashMap<String, Integer> wordLetterCount) {
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

    public void questionThree() {
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

    public void questionTwo() {
        String fileName = System.getProperty("user.dir") + "\\src\\advent\\code\\data.txt";

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

    public void questionOne() {
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
