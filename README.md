
# NotesApp

NotesApp is an Android application that allows users to create, edit, and delete notes. The app uses Room database for local data storage and provides a simple and intuitive user interface for managing notes.

## Features

- Add new notes with a title and content.
- Edit existing notes.
- Delete notes by swiping left or right.
- View notes in a grid layout.

## Technologies Used

- **Android SDK**
- **Room Database** for local storage
- **RecyclerView** for displaying notes
- **Material Design** for UI components

## Screenshots

![Main Screen]()
![Add Note](https://github.com/Sajjad01-chaus/Notes-App/assets/148811684/8c607016-fcad-463c-a3a7-a75719e635cb)
add_note.png)
![Edit Note](screenshots/edit_note.png)

## Getting Started

To get a local copy up and running follow these simple steps.

### Prerequisites

- Android Studio
- Android device or emulator

### Installation

1. **Clone the repo**
    ```sh
    git clone https://github.com/yourusername/notesapp.git
    ```
2. **Open the project in Android Studio**
3. **Build and run the project** on an Android device or emulator.

## Usage

### Adding a Note

1. Click on the floating action button (+) to add a new note.
2. Enter the title and content of the note.
3. Click on the save button to save the note.

### Editing a Note

1. Tap on any note in the grid to open it in the edit screen.
2. Modify the title and content as needed.
3. Click on the save button to update the note.

### Deleting a Note

1. Swipe left or right on any note in the grid to delete it.

## Code Overview

### MainActivity

The `MainActivity` is the entry point of the application. It sets up the RecyclerView with a GridLayoutManager and handles adding, editing, and deleting notes.

```java
public class MainActivity extends AppCompatActivity {
    // Code for MainActivity
}
```

### NoteAdapter

The `NoteAdapter` binds the note data to the RecyclerView and handles the click events for editing notes.

```java
public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.NoteViewHolder> {
    // Code for NoteAdapter
}
```

### NoteDatabase

The `NoteDatabase` class sets up the Room database and provides an instance of the `NoteDao`.

```java
@Database(entities = {Note.class}, version = 1, exportSchema = false)
public abstract class NoteDatabase extends RoomDatabase {
    // Code for NoteDatabase
}
```

### NoteDao

The `NoteDao` interface defines the database operations for the `Note` entity.

```java
@Dao
public interface NoteDao {
    // Code for NoteDao
}
```

### Note

The `Note` class represents the note entity in the database.

```java
@Entity(tableName = "note_table")
public class Note {
    // Code for Note
}
```

### Add_New Activity

The `Add_New` activity handles adding and editing notes.

```java
public class Add_New extends AppCompatActivity {
    // Code for Add_New
}
```

## Contributing

Contributions are what make the open-source community such an amazing place to be learn, inspire, and create. Any contributions you make are **greatly appreciated**.

1. Fork the Project
2. Create your Feature Branch (`git checkout -b feature/AmazingFeature`)
3. Commit your Changes (`git commit -m 'Add some AmazingFeature'`)
4. Push to the Branch (`git push origin feature/AmazingFeature`)
5. Open a Pull Request
