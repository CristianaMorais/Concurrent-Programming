# Programação Concorrente (CC3037), 2019/20

## Objetivos do trabalho
• Programar, validar a correção, e avaliar o desempenho de filas concorrentes com elementos
guardados num array, com diversos tipos de aproximações - baseadas em “locks”, primitivas
atómicas ou STM - e modalidades de implementação - capacidade fixa ou ilimitada.

• Programar um “crawler” concorrente de páginas Web baseado no uso de uma “fork-join pool”.

## Implementação
Java e Cooperari.

## Modo de utilização
É necessário ter o OpenJDK para java 8 instalado.

### Comandos
---------------------------------------------
| Material do projecto                      |
| Programação Concorrente (CC3037), 2019/20 |
| Eduardo R.B. Marques, DCC/FCUP            |


---------------
| Directórios |

```bash
src => código

  src/pc/bqueue  
    => Código para implementação de filas

  src/pc/crawler 
    => Código para implementação do crawler

  src/pc/util    
    => Outras classes utilitárias

Outras pastas

  classes       
    => código compilado (ficheiros .class)

  lib           
    => arquivos JAR para uso do ScalaSTM

  cooperari-0.3 
    => versão 0.3 do Cooperari com alguns scripts afinados

 _ajdump, cdata, cooperari-test-data 
    => directórios usados pelo Cooperari
```
-----------
| Scripts |

```bash
Genéricos

  compile.sh 
    => Compila todo o código 

  run.sh nome_da_classe argumentos 
    => executa programa

  clean.sh 
    => limpa todo o código compilado e directórios do Cooperari

Trabalho com filas

  ctests.sh 
    => Executa testes de filas em modo cooperativo

  ptests.sh 
    => Executa testes de filas em modo preemptivo

  qbench.sh 
    => Executa programa de benchmark para filas

  rdemo.sh
    => Executa programa pc.bqueue.RoomsDemo


Trabalho com crawler

  wserver.sh 
    => executa web server 

  scrawl.sh 
    => executa crawler sequencial

  ccrawl.sh 
    => executa crawler concorrente

Scripts auxiliares (não precisa de corrê-los directamente)

  cjunitp.sh, cjunit.sh, cjavac.sh, env.sh 
```

## Autoras
| Nome                            | Nº Mecanográfico   |
| ------------------------------- | -------------------| 
| Sara Daniela Ferreira de Sousa  | up201504217        |
| Cristiana Morais da Silva       | up201505454        |
