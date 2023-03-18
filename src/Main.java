import java.util.*;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) {
        List<String> names = Arrays.asList("Jack", "Connor", "Harry", "George", "Samuel", "John");
        List<String> families = Arrays.asList("Evans", "Young", "Harris", "Wilson", "Davies", "Adamson", "Brown");
        Collection<Person> persons = new ArrayList<>();
        for (int i = 0; i < 10_000_000; i++) {
            persons.add(new Person(
                    names.get(new Random().nextInt(names.size())),
                    families.get(new Random().nextInt(families.size())),
                    new Random().nextInt(100),
                    Sex.values()[new Random().nextInt(Sex.values().length)],
                    Education.values()[new Random().nextInt(Education.values().length)])
            );
        }
        System.out.println("Всего жителей : " + persons.size());
        long count = persons.stream()
                .filter(person -> person.getAge() < 18)
                .count();
        System.out.println("Количество несовершеннолетних (т.е. людей младше 18 лет) :  " + count);


        List<String> prizivnik = persons.stream()
                .filter(person -> person.getAge() < 27)
                .filter(person -> person.getAge() >= 18)
                .map(Person::getFamily)
                .toList();
        System.out.println("Количество призывников  :  " + prizivnik.size());

        List<String> worker = persons.stream()
                .filter(person -> person.getEducation() == Education.HIGHER)
                .filter(person -> person.getAge() >= 18)
                .filter(
                        (person) -> (person.getSex() == Sex.WOMAN && person.getAge() < 60) || (person.getSex() == Sex.MAN && person.getAge() < 65))
                .sorted(Comparator.comparing(Person::getFamily))
                .map(Person::getFamily)
                .collect(Collectors.toList());
        System.out.println("     Количество  потенциально работоспособных людей с в/о:  " + worker.size());
        familiesWorker(worker);
    }

    private static void familiesWorker(List<String> worker) {
        int sum = 0;
        int i = 0;
        while (i < worker.size()) {
            String suname = worker.get(i);
            int pos = worker.lastIndexOf(suname);
            int quantity = pos - i + 1;
            sum = sum + quantity;
            i = pos + 1;
            System.out.println(suname + " в количестве " + quantity);
        }
        System.out.println("      Итого            " + sum);
    }
}


