package com.udacity.java_joke_lib;

import java.util.Random;

public class Joker {

  private static final String[] jokes = {
    "Don’t worry if it doesn’t work right. If everything did, you’d be out of a job. (Mosher’s Law of Software Engineering)",
    "The best performance improvement is the transition from the nonworking state to the working state. (J. Osterhout)",
    "Programming today is a race between software engineers striving to build bigger and better idiot-proof programs, and the universe trying to produce bigger and better idiots. So far, the universe is winning. (Rick Cook)",
    "If debugging is the process of removing software bugs, then programming must be the process of putting them in. (Edsger Dijkstra)",
    "Walking on water and developing software from a specification are easy if both are frozen. (Edward V Berard)",
    "Without requirements or design, programming is the art of adding bugs to an empty text file. (Louis Srygley)",
    "The best thing about a boolean is even if you are wrong, you are only off by a bit. (Anonymous)",
    "A SQL query goes into a bar, walks up to two tables and asks, 'Can I join you?'",
    "How many programmers does it take to change a light bulb ? None, that's a hardware problem",
    "Command line Russian roulette : [ $[ $RANDOM % 6 ] == 0 ] && rm -rf / || echo *Click*",
    "In theory, there ought to be no difference between theory and practice. In practice, there is.",
    "Why did the programmer quit his job? Because he didn't get arrays.",
    "I � Unicode."
  };

  public String getJoke() {
    int size = jokes.length;
    Random r = new Random();
    int index = r.nextInt(size);
    return jokes[index];
  }
}
