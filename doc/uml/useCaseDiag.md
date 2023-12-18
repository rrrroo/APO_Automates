```mermaid
flowchart LR
    Utilisateur --- id1([1_jeu1D])
    Utilisateur---id2([2_regle_de_majorite])
    Utilisateur---id3([3_jeu_de_la_vie])
    Utilisateur---id4([4_jeu_foret])
    Utilisateur---id5([5_definir_nouvel_automate])


    subgraph app
    id1([1_jeu1D])

    id2([2_regle_de_majorite])
    id2([2_regle_de_majorite])---id21([choix_parametres2])---id0_1([afficher_etapes])

    id3([3_jeu_de_la_vie])
    id3([3_jeu_de_la_vie])---id31([choix_parametres3])---id0_1([afficher_etapes])

    id4([4_jeu_foret])---id0_1([afficher_etapes])
    id4([4_jeu_foret])---id41([choix_parametres4])

    id5([5_definir_nouvel_automate])---id0_1([afficher_etapes])
    id5([5_definir_nouvel_automate])---id51([choix_parametres5])

    end

    %%USer1---1d1([choix_regle])
    %%USer2---1d2([choix_taille])
    USer1---1d3([lancer_sim])
    subgraph 1_jeu1Dchoix_parametres1
    1d3([lancer_sim])--include-->1d1([choix_regle])
    1d3([lancer_sim])--include-->1d2([choix_taille])
    end


    USer2---2d3([lancer_sim])
    subgraph 2_regle_de_majoritechoix_parametres2
    2d3([lancer_sim])--include-->2d2([choix_taille])
    end




    User111 --- menu111
    User111 --- choix_parametre111

    subgraph app111
    1_jeu1D11--- .
    2_regle_de_majorite11---.
    3_jeu_de_la_vie11---.
    4_jeu_foret11---.
    5_definir_nouvel_automate11---.
    . --> menu11
    1_jeu1D11---choix_parametre11


    end











    %% https://mermaid.js.org/syntax/flowchart.html

