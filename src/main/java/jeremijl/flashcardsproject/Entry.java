package jeremijl.flashcardsproject;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import java.util.Objects;

@Entity
public class Entry {

    @Id
    private Long idTranslation;

    private String english;
    private String german;
    private String polish;

    public Entry(Long idTranslation, String english, String german, String polish) {
        this.idTranslation = idTranslation;
        this.english = english;
        this.german = german;
        this.polish = polish;
    }

    public Entry() {
    }

    @Override
    public String toString() {
        return idTranslation + ". " + english + " - " + german + " - " + polish;
    }

    public String[] toArray() {
        return new String[]{english,german,polish};
    }

    @Override
    public boolean equals(Object obj) {

        if (obj == null || getClass() != obj.getClass())
            return false;
        else {
            Entry e = (Entry) obj;
            return this.idTranslation.equals(e.idTranslation);
        }
    }

    @Override
    public int hashCode() {
        return Objects.hash(idTranslation);
    }

    public void setIdTranslation(Long idTranslation) {
        this.idTranslation = idTranslation;
    }

    @Id
    public Long getIdTranslation() {
        return idTranslation;
    }

    public String getEnglish() {
        return english;
    }

    public void setEnglish(String english) {
        this.english = english;
    }

    public String getGerman() {
        return german;
    }

    public void setGerman(String german) {
        this.german = german;
    }

    public String getPolish() {
        return polish;
    }

    public void setPolish(String polish) {
        this.polish = polish;
    }
}
