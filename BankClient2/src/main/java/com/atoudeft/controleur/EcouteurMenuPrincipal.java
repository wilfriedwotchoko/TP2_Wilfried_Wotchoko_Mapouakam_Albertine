package com.atoudeft.controleur;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

import com.atoudeft.client.Client;
import com.atoudeft.vue.PanneauConfigServeur;

/**
 *
 * @author Abdelmoumène Toudeft (Abdelmoumene.Toudeft@etsmtl.ca)
 * @version 1.0
 * @since 2024-11-01
 */
public class EcouteurMenuPrincipal implements ActionListener {
    private Client client;
    private JFrame fenetre;

    public EcouteurMenuPrincipal(Client client, JFrame fenetre) {
        this.client = client;
        this.fenetre = fenetre;
    }

    @Override
    public void actionPerformed(ActionEvent evt) {
        Object source = evt.getSource();
        String action;
        String alias;
        int res;

        if (source instanceof JMenuItem) {
            action = ((JMenuItem)source).getActionCommand();
            switch (action) {
                case "CONNECTER":
                    if (!client.isConnecte()) {
                        if (!client.connecter()) {
                            JOptionPane.showMessageDialog(fenetre, "Le serveur ne répond pas");
                            break;
                        }
                    }
                    break;
                case "DECONNECTER":
                    if (!client.isConnecte())
                        break;
                    res = JOptionPane.showConfirmDialog(fenetre, "Vous allez vous déconnecter",
                            "Confirmation Déconnecter",
                            JOptionPane.YES_NO_OPTION,JOptionPane.WARNING_MESSAGE);
                    if (res == JOptionPane.OK_OPTION){
                        client.deconnecter();
                    }
                    break;
                case "CONFIGURER":
                    //TODO : compléter (question 1.3)
                    boolean valide = false;

                    while (!valide) {
                        // Créer et afficher le panneau de configuration
                        PanneauConfigServeur panneauConfig = new PanneauConfigServeur(client.getAdrServeur(), client.getPortServeur());
                        int option = JOptionPane.showConfirmDialog(
                                fenetre,
                                panneauConfig,
                                "Configuration du serveur",
                                JOptionPane.OK_CANCEL_OPTION,
                                JOptionPane.PLAIN_MESSAGE
                        );

                        if (option == JOptionPane.OK_OPTION) {
                            try {
                                String nouvelleAdr = panneauConfig.getAdresseServeur();
                                int nouveauPort = Integer.parseInt(panneauConfig.getPortServeur());
                                client.setAdrServeur(nouvelleAdr);
                                client.setPortServeur(nouveauPort);
                                valide = true; 
                            } catch (NumberFormatException e) {
                                // Afficher une boîte d'erreur si le port n'est pas un entier
                                JOptionPane.showMessageDialog(
                                        fenetre,
                                        "Le port doit être un entier valide.",
                                        "Erreur",
                                        JOptionPane.ERROR_MESSAGE
                                );
                            }
                        } else {
                            valide = true;
                        }
                    }
                    break;
                case "QUITTER":
                    if (client.isConnecte()) {
                        res = JOptionPane.showConfirmDialog(fenetre, "Vous allez vous déconnecter",
                                "Confirmation Quitter",
                                JOptionPane.YES_NO_OPTION,JOptionPane.WARNING_MESSAGE);
                        if (res == JOptionPane.OK_OPTION){
                            client.deconnecter();
                            System.exit(0);
                        }
                    }
                    else
                        System.exit(0);
                    break;
            }
        }
    }
}