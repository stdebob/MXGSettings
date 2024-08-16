import os

def get_secret(key):
  """Fetches a secret from the environment."""
  return os.environ.get(key, None)

if __name__ == "__main__":
  PASSWD = get_secret("KEY_STORE_PASSWORD")
  print(f"keyPassword=/{PASSWD}/")
  print(f"storePassword=/{PASSWD}/")
