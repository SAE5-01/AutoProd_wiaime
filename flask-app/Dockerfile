# ==== Build/run image ====
FROM python:3.12-slim

# 1) Dépendances système minimales (certificats + build essentials si besoin)
RUN apt-get update && apt-get install -y --no-install-recommends \
    build-essential curl ca-certificates && \
    rm -rf /var/lib/apt/lists/*

# 2) Répertoire de travail
WORKDIR /app

# 3) Copier les requirements et installer (couche cache)
#   -> crée un requirements.txt si tu n'en as pas (voir plus bas)
COPY requirements.txt .
RUN pip install --no-cache-dir -r requirements.txt

# 4) Copier le code
COPY . .

# 5) Variables d'env (tu peux les surcharger au runtime)
ENV PYTHONDONTWRITEBYTECODE=1 \
    PYTHONUNBUFFERED=1 \
    PORT=5000

# 6) Exposer le port
EXPOSE 5000

# 7) Commande (Gunicorn prod si "app" existe dans app.py ; sinon: python app.py)
#    Décommente la ligne qui te convient :
#CMD ["python","app.py"]
CMD ["gunicorn", "-w", "2", "-b", "0.0.0.0:5000", "app:app"]
