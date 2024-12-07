package com.atoudeft.controleur;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;

import com.atoudeft.client.Client;
import com.atoudeft.vue.PanneauPrincipal;

public class EcouteurOperationsCompte implements ActionListener {
    private Client client;
    private PanneauPrincipal panneauPrincipal;

    public EcouteurOperationsCompte(Client client, PanneauPrincipal panneauPrincipal) {
        this.client = client;
        this.panneauPrincipal= panneauPrincipal;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String action = e.getActionCommand();

            if ("EPARGNE".equals(action)) {
            if (client.isConnecte()) {
                client.envoyer("EPARGNE");
            } else {
                JOptionPane.showMessageDialog(panneauPrincipal, "Le client n'est pas connecté.", "Erreur", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}
