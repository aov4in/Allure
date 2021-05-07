package ru.netology.test;

import com.github.javafaker.Faker;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class DataGenerator {

    private DataGenerator(){}

    public static Faker faker = new Faker(new Locale("ru"));

    public static String getRandomCity(){
        List<String> list = Arrays.asList("Абакан", "Анадырь", "Архангельск", "Астрахань", "Барнаул", "Белгород", "Биробиджан", "Благовещенск", "Брянск",
                "Великий Новгород", "Владивосток", "Владикавказ", "Владимир", "Волгоград", "Вологда", "Воронеж", "Горно-Алтайск",
                "Грозный", "Екатеринбург", "Иваново", "Ижевск", "Иркутск", "Йошкар-Ола", "Казань", "Калининград", "Калуга", "Кемерово",
                "Киров", "Кострома", "Краснодар", "Красноярск", "Курган", "Курск", "Кызыл", "Липецк", "Магадан", "Магас", "Майкоп",
                "Махачкала", "Москва", "Мурманск", "Нальчик", "Нарьян-Мар", "Нижний Новгород", "Новосибирск", "Омск", "Орёл", "Оренбург",
                "Пенза", "Пермь", "Петрозаводск", "Петропавловск-Камчатский", "Псков", "Ростов-на-Дону", "Рязань", "Салехард", "Самара",
                "Санкт-Петербург", "Саранск", "Саратов", "Севастополь", "Симферополь", "Смоленск", "Ставрополь", "Сыктывкар",
                "Тамбов", "Тверь", "Томск", "Тула", "Тюмень", "Улан-Удэ", "Ульяновск", "Уфа", "Хабаровск", "Ханты-Мансийск", "Чебоксары",
                "Челябинск", "Черкесск", "Чита", "Элиста", "Южно-Сахалинск", "Якутск", "Ярославль");
        Random random = new Random();
        String randomCity = list.get(random.nextInt(list.size()));
        return randomCity;
    }

    public static String getCorrectDate(int days) {
        String dateOfDelivery = LocalDate.now().plusDays(days).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
        return dateOfDelivery;
    }

    public static String getNotCorrectDate() {
        String notCorrectDate = LocalDate.now().format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
        return notCorrectDate;
    }

    public static String getRandomName() {
        String name = faker.name().lastName() + " " + faker.name().firstName();
        return name;
    }

    public static String getRandomPhone() {
        String phone = faker.phoneNumber().phoneNumber();
        return phone;
    }

}
