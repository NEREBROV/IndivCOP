package db;

import models.Model;

import java.io.IOException;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

abstract public class ModelStorage {
    // Данный класс является интерфейсом взаимодействия с объектами моделей.
    // Он реализует методы вставки, изменения, получения и удаления объектов базе.

    // Поле, которое хранит ссылку на объект хранилища базы данных
    DBStore dbStore;

    // Поле для хранения объектов модели,
    // при переопределении метода parceModel тут хранятся объекты только одного типа
    List<Model> objects;

    public ModelStorage(DBStore dbStore) {
        this.dbStore = dbStore;

        // Фильтрация объектов строк, хранящихся в объекте хранилища по ключевому признаку
        this.objects = dbStore.getObjectsStrings().filter(
                s -> s.contains(getDbFilteringKey())
        ).map(
                // К каждой строке в наборе применяем функцию парсинга модели
                this::parseModel
        ).collect(
                // Собираем все в список
                Collectors.toList()
        );
    }

    public List<Model> getAll() {
        return objects;
    }

    public Model getById(Integer id) throws IllegalArgumentException {
        Optional<Model> result = objects.stream().filter(model -> model.getId().equals(id)).findFirst();
        if (result.isPresent()) {
            return result.get();
        }
        // Если объект отсутствует, выбрасываем исключение
        throw new IllegalArgumentException("Object with passed ID doesn't exists");
    }

    public void insert(Model model) throws IllegalArgumentException, IOException {
        //Проверяем есть ли объект в базе данных.
        //Если есть, выбрасываем исключение.
        if (objects.stream().anyMatch(m -> m.getId().equals(model.getId()))) {
            throw new IllegalArgumentException("This object already exists");
        }

        //Т.к. такого объекта нет, то добавляем его в последовательность objects.
        this.objects = Stream.concat(
                objects.stream(),
                Stream.of(model)
        ).collect(Collectors.toList());

        // И добавляем строковую презентацию объекта в последовательность objects объекта dbStore
        dbStore.setObjectsStrings(
                Stream.concat(
                        dbStore.getObjectsStrings(),
                        Stream.of(getDbRepresentation(model))
                )
        );

        dbStore.commitChanges();
    }

    public void update(Model model) throws NoSuchElementException, IOException {
        //Проверяем есть ли объект в базе данных.
        //Если нет, выбрасываем исключение.
        boolean check = objects.stream().noneMatch(
                m -> m.getId().equals(model.getId())
        );
        if (check) {
            throw new NoSuchElementException("This object doesn't exist");
        }

        // Изменим объект в последовательности объектов,
        // удалив его старую версию и добавив новую
        this.objects = Stream.concat(
                objects.stream().filter(m -> !m.getId().equals(model.getId())),
                Stream.of(model)
        ).collect(Collectors.toList());

        // Изменяем презентацию объекта в поле objectString объекта dbStore

        dbStore.setObjectsStrings(
                Stream.concat(
                        dbStore.getObjectsStrings().filter(s -> !s.contains(getIdentificationString(model))),
                        Stream.of(getDbRepresentation(model))
                )

        );
        dbStore.commitChanges();
    }

    public void delete(Model model) throws IOException {
        // Удаляем объект из objects
        this.objects = objects.stream().filter(
                m -> !m.getId().equals(model.getId())
        ).collect(Collectors.toList());

        // Удаляем объект из objectString объекта dbStore
        dbStore.setObjectsStrings(
                dbStore.getObjectsStrings().filter(s -> s.contains(getIdentificationString(model)))
        );
        dbStore.commitChanges();
    }

    private String getIdentificationString(Model model) {
        return String.format("%s\t%d", getDbFilteringKey(), model.getId());
    }

    /*
        Здесь используется паттерн "Абстрактный метод",
        который должен быть переопределн в потомке
        для представления объекта из строки в базе данных,
        и для получения строки базы данных из объекта
    */
    abstract protected Model parseModel(String string);

    abstract protected String getDbFilteringKey();

    abstract protected String getDbRepresentation(Model model);
}
