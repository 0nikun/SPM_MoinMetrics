package de.kaufdort.moinmetrics.repository;


import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;
import weka.core.Instances;


@Component
@SessionScope
public class StorageSession {

    private Instances einkaeufe;


    public void clearEinkaeufe() {
        this.einkaeufe.clear();
    }

    public int getEinkaeufeSize() {
        return einkaeufe.size();
    }

    public void setInstances(Instances instances) {
        this.einkaeufe = instances;
    }

    public Instances getInstances() {
        return einkaeufe;
    }

}
