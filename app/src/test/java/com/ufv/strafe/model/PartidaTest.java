package com.ufv.strafe.model;

import junit.framework.TestCase;

import org.junit.Assert;
import org.junit.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

public class PartidaTest {

    Usuario usuario = new Usuario("", "usuario", "", 0.0, 0, 0);

    Partida partida = new Partida(
            "Cruzeiro",
            "Atletico",
            "Overwatch",
            "16/02/2022 12:00:00",
            "20/02/2022 12:39:00",
            "1",
            "Cruzeiro"
    );
    Aposta aposta = new Aposta("aposta1",
            "Cruzeiro",
            20.0,
            2.0,
            "usuario1");

    Aposta aposta2 = new Aposta("aposta2",
            "Atletico",
            10.0,
            2.0,
            "usuario1");

    @Test
    public void testGetSaldoAposta() {

        partida.addAposta(aposta);

        assert (40.0 == partida.getSaldoAposta(aposta.getId()));

    }

    @Test
    public void apostaPerdeTest(){


        partida.addAposta(aposta2);

        assert (0.0 == partida.getSaldoAposta(aposta2.getId()));
    }

    @Test
    public void  useRecebeApostas() throws ParseException {
        partida.addAposta(aposta);
        partida.addAposta(aposta2);
        usuario.addAposta(partida.getId(), aposta.getId(), aposta.getValor()); // -20
        usuario.addAposta(partida.getId(), aposta2.getId(), aposta2.getValor()); // -10

        Double vAposta = 0.0;
        for (String a : Objects.requireNonNull(usuario.getApostas().get(partida.getId()))) {
            vAposta += partida.getSaldoAposta(a); // + 40
        }
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        Date dataFim = dateFormat.parse(partida.getDataFim());
        Date dataAtual = new Date();

        if (dataAtual.after(dataFim) || dataAtual.equals(dataFim)) {
            if (vAposta > 0.0) {
                usuario.setSaldo(usuario.getSaldo() + vAposta);
            }
        }

        assert (usuario.getSaldo() == 10);
    }
}