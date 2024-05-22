CREATE TABLE IF NOT EXISTS "auto_answer" (
    "auto_answer_id" SERIAL PRIMARY KEY,
    "auto_question_id" INTEGER REFERENCES "auto_question"("auto_question_id") ON DELETE CASCADE,
    "answer_id" INTEGER REFERENCES "answer"("answer_id") ON DELETE CASCADE,
    "sequence_order" INTEGER
);