resource "aws_subnet" "miracle_public_a" {
  vpc_id     = aws_vpc.miracle_vpc.id
  cidr_block = "10.0.1.0/24"
  map_public_ip_on_launch = true
  availability_zone = "ap-northeast-2a"
  tags = {
    Name = "miracle-public-a"
  }
}

resource "aws_subnet" "miracle_public_c" {
  vpc_id     = aws_vpc.miracle_vpc.id
  cidr_block = "10.0.2.0/24"
  map_public_ip_on_launch = true
  availability_zone = "ap-northeast-2c"
  tags = {
    Name = "miracle-public-c"
  }
}

resource "aws_subnet" "miracle_private_a" {
  vpc_id     = aws_vpc.miracle_vpc.id
  cidr_block = "10.0.3.0/24"
  availability_zone = "ap-northeast-2a"
  tags = {
    Name = "miracle-private-a"
  }
}

resource "aws_subnet" "miracle_private_c" {
  vpc_id     = aws_vpc.miracle_vpc.id
  cidr_block = "10.0.4.0/24"
  availability_zone = "ap-northeast-2c"
  tags = {
    Name = "miracle-private-c"
  }
}