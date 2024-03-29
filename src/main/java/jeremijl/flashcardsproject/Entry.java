package jeremijl.flashcardsproject;

import java.util.Map;
import java.util.Objects;

public class Entry {

    public Map<Lang,String> translationMap;

    public Entry(Map<Lang,String> translationMap) {
        this.translationMap = translationMap;
    }

    @Override
    public String toString() {
        return selectedLangFormat(Lang.values());
    }

    public String toCSV() {
        return translationMap.get(Lang.ENGLISH) + ";" +
                translationMap.get(Lang.GERMAN) + ";" + translationMap.get(Lang.POLISH);

    }

    public String selectedLangFormat(Lang ... languages){
        StringBuilder builder = new StringBuilder();
        for (Lang l : languages)
            builder.append(translationMap.get(l)).append(" - ");

        //Cut last hyphen sign
        builder.replace(builder.lastIndexOf(" - "), builder.length(), "");

        return builder.toString();
    }

    @Override
    public boolean equals(Object obj) {

        if (obj == null || getClass() != obj.getClass())
            return false;
        else {
            Entry entry = (Entry) obj;
            return (
                    this.translationMap.get(Lang.ENGLISH).equals(entry.translationMap.get(Lang.ENGLISH)) &&
                    this.translationMap.get(Lang.POLISH).equals(entry.translationMap.get(Lang.POLISH)) &&
                    this.translationMap.get(Lang.GERMAN).equals(entry.translationMap.get(Lang.GERMAN)));
        }
    }

    @Override
    public int hashCode() {
        return Objects.hash(translationMap);
    }
}
