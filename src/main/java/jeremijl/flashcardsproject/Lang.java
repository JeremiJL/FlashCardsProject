package jeremijl.flashcardsproject;

enum Lang {
    ENGLISH,GERMAN,POLISH;


    public static Lang selectRandomLanguage(){
        int randomIndex = (int)(Math.random() * Lang.values().length);
        int i = 0;
        for (Lang l : Lang.values()){
            if (i++ == randomIndex)
                return l;
        }
        //else something bad must have happened
        return null;
    }
}

