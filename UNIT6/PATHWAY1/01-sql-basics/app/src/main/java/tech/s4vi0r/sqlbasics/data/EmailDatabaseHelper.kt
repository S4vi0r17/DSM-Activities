package tech.s4vi0r.sqlbasics.data

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class EmailDatabaseHelper(context: Context) : SQLiteOpenHelper(
    context,
    DATABASE_NAME,
    null,
    DATABASE_VERSION
) {
    companion object {
        private const val DATABASE_NAME = "email.db"
        private const val DATABASE_VERSION = 1
        private const val TABLE_EMAIL = "email"

        // Columnas de la tabla email
        const val COLUMN_ID = "id"
        const val COLUMN_SUBJECT = "subject"
        const val COLUMN_SENDER = "sender"
        const val COLUMN_FOLDER = "folder"
        const val COLUMN_STARRED = "starred"
        const val COLUMN_READ = "read"
        const val COLUMN_RECEIVED = "received"
    }

    override fun onCreate(db: SQLiteDatabase) {
        val createTableQuery = """
            CREATE TABLE $TABLE_EMAIL (
                $COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT,
                $COLUMN_SUBJECT TEXT NOT NULL,
                $COLUMN_SENDER TEXT NOT NULL,
                $COLUMN_FOLDER TEXT NOT NULL,
                $COLUMN_STARRED INTEGER NOT NULL DEFAULT 0,
                $COLUMN_READ INTEGER NOT NULL DEFAULT 0,
                $COLUMN_RECEIVED INTEGER NOT NULL
            )
        """.trimIndent()

        db.execSQL(createTableQuery)
        insertSampleData(db)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_EMAIL")
        onCreate(db)
    }

    private fun insertSampleData(db: SQLiteDatabase) {
        val sampleEmails = listOf(
            Email("To be or not to be?", "hamlet@example.com", "inbox", false, false),
            Email("The course of true love never did run smooth", "hermia@example.com", "inbox", true, true),
            Email("Lord, what fools these mortals be!", "puck@example.com", "important", true, true),
            Email("All the world's a stage", "jaques@example.com", "inbox", false, false),
            Email("Some are born great, some achieve greatness", "malvolio@example.com", "inbox", false, true),
            Email("The lady doth protest too much", "gertrude@example.com", "spam", false, false),
            Email("Brevity is the soul of wit", "polonius@example.com", "inbox", false, true),
            Email("If music be the food of love, play on", "orsino@example.com", "important", true, false),
            Email("Double, double toil and trouble", "witches@example.com", "spam", false, false),
            Email("Something is rotten in the state of Denmark", "marcellus@example.com", "inbox", false, true),
            Email("The better part of valor is discretion", "falstaff@example.com", "inbox", false, false),
            Email("Cowards die many times before their deaths", "caesar@example.com", "important", true, true),
            Email("Friends, Romans, countrymen, lend me your ears", "antony@example.com", "inbox", true, false),
            Email("Et tu, Brute?", "caesar@example.com", "trash", false, true),
            Email("Now is the winter of our discontent", "richard@example.com", "inbox", false, false),
            Email("A horse! A horse! My kingdom for a horse!", "richard@example.com", "inbox", false, false),
            Email("Out, out, brief candle!", "macbeth@example.com", "important", false, true),
            Email("Is this a dagger which I see before me?", "macbeth@example.com", "inbox", true, false),
            Email("What's in a name? That which we call a rose", "juliet@example.com", "inbox", false, true),
            Email("O Romeo, Romeo! Wherefore art thou Romeo?", "juliet@example.com", "inbox", true, false),
            Email("Parting is such sweet sorrow", "juliet@example.com", "sent", false, true),
            Email("The fool doth think he is wise", "touchstone@example.com", "inbox", false, false),
            Email("Lord, what a fool am I!", "launcelot@example.com", "spam", false, false),
            Email("Better a witty fool than a foolish wit", "feste@example.com", "inbox", false, true),
            Email("We know what we are, but know not what we may be", "ophelia@example.com", "inbox", false, false),
            Email("The rest is silence", "hamlet@example.com", "important", true, true),
            Email("Good night, sweet prince", "horatio@example.com", "inbox", false, true),
            Email("Neither a borrower nor a lender be", "polonius@example.com", "inbox", false, false),
            Email("To thine own self be true", "polonius@example.com", "important", true, true),
            Email("Methinks the lady protests too much", "hamlet@example.com", "inbox", false, false),
            Email("There are more things in heaven and earth", "hamlet@example.com", "inbox", true, false),
            Email("Though this be madness, yet there is method in it", "polonius@example.com", "inbox", false, true),
            Email("Get thee to a nunnery", "hamlet@example.com", "trash", false, false),
            Email("The play's the thing", "hamlet@example.com", "inbox", false, true),
            Email("Alas, poor Yorick!", "hamlet@example.com", "inbox", false, false),
            Email("How sharper than a serpent's tooth", "lear@example.com", "inbox", true, true),
            Email("Nothing will come of nothing", "lear@example.com", "important", false, false),
            Email("Age cannot wither her, nor custom stale", "enobarbus@example.com", "inbox", false, true),
            Email("My salad days", "cleopatra@example.com", "inbox", false, false),
            Email("The barge she sat in", "enobarbus@example.com", "inbox", false, true),
            Email("I have immortal longings in me", "cleopatra@example.com", "important", true, false),
            Email("Uneasy lies the head that wears a crown", "henry@example.com", "inbox", false, true),
            Email("Once more unto the breach", "henry@example.com", "inbox", true, false)
        )

        sampleEmails.forEachIndexed { index, email ->
            val values = ContentValues().apply {
                put(COLUMN_SUBJECT, email.subject)
                put(COLUMN_SENDER, email.sender)
                put(COLUMN_FOLDER, email.folder)
                put(COLUMN_STARRED, if (email.starred) 1 else 0)
                put(COLUMN_READ, if (email.read) 1 else 0)
                // Timestamps decrementales para simular correos recibidos en diferentes momentos
                put(COLUMN_RECEIVED, System.currentTimeMillis() - (index * 3600000L))
            }
            db.insert(TABLE_EMAIL, null, values)
        }
    }

    data class Email(
        val subject: String,
        val sender: String,
        val folder: String,
        val starred: Boolean,
        val read: Boolean
    )
}
