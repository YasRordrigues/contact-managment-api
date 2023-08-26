provider "aws" {
  region  = "us-west-1"
  access_key = var.AWS_ACCESS_KEY
  secret_key = var.AWS_SECRET_KEY
}

resource "aws_key_pair" "deployer" {
  key_name   = "deployer-key"
  public_key = file("deployer_key.pub")
}

resource "aws_security_group" "app_sg" {
  name        = "AppSecurityGroup"
  description = "Allow inbound traffic for our app"

  ingress {
    from_port   = 80
    to_port     = 80
    protocol    = "tcp"
    cidr_blocks = ["0.0.0.0/0"]
  }

  ingress {
    from_port   = 22
    to_port     = 22
    protocol    = "tcp"
    cidr_blocks = ["0.0.0.0/0"]
  }
}

resource "aws_instance" "app_server" {
  ami           = "513740308471" # use the ID for Amazon Linux 2 LTS or other
  instance_type = "t2.micro"
  key_name      = aws_key_pair.deployer.key_name
  vpc_security_group_ids = [aws_security_group.app_sg.id]

  tags = {
    Name = "AppServer"
  }
}

output "public_ip" {
  value = aws_instance.app_server.public_ip
}